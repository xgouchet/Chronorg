package fr.xgouchet.khronorg.feature.timeline

import android.graphics.Color
import android.util.Log
import fr.xgouchet.khronorg.commons.query.QueryBuilder
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.commons.time.getLocalTimeZone
import fr.xgouchet.khronorg.feature.events.Event
import fr.xgouchet.khronorg.feature.jumps.Jump
import fr.xgouchet.khronorg.feature.jumps.JumpRepository
import fr.xgouchet.khronorg.feature.portals.Portal
import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.provider.KhronorgSchema
import fr.xgouchet.khronorg.ui.presenters.BaseListPresenter
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import org.joda.time.DateTimeFieldType
import java.util.*

/**
 * @author Xavier F. Gouchet
 */
class ShardListPresenter(val travellerRepository: BaseRepository<Traveller>,
                         val jumpRepository: JumpRepository,
                         val eventRepository: BaseRepository<Event>,
                         val portalRepository: BaseRepository<Portal>,
                         val project: Project,
                         navigator: ShardNavigator)
    : BaseListPresenter<TimelineShard>(navigator) {


    private var portalsDisposable: Disposable? = null


    override fun subscribe() {
        loadPortals()
    }

    fun loadPortals() {
        portalsDisposable?.dispose()
        portalsDisposable = getPortalsObservable()
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it -> onPortalsLoaded(it) })
    }

    private fun onPortalsLoaded(portals: List<Portal>) {
        view?.let {
            (it as ShardListFragment).setPortals(portals)
        }
        load(true)
    }

    fun getPortalsObservable(): Observable<Portal> {
        val alteration = QueryBuilder.where {
            equals(KhronorgSchema.COL_PROJECT_ID, project.id.toString())
        }

        return portalRepository.getWhere(alteration)
    }

    override fun getItemsObservable(): Observable<TimelineShard> {
        val query = QueryBuilder.where {
            equals(KhronorgSchema.COL_PROJECT_ID, project.id.toString())
        }
        val eventShardsObservable: Observable<TimelineShard> = eventRepository.getWhere(query)
                .subscribeOn(Schedulers.io())
                .map {
                    event ->
                    val id = SHARD_EVENT or event.id.toLong()
                    TimelineShard(event.instant, event.name, event.color, TimelineShard.ShardType.SINGLE, id)
                }

        val jumpShardsObservable: Observable<TimelineShard> = travellerRepository.getWhere(query)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMap { traveller ->
                    return@flatMap travellerToShards(traveller)
                }

        val yearsShardsObservable: Observable<TimelineShard> = Observable.just(project)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .flatMap { project -> Observable.create(projectToYearShards(project)) }


        return Observable.mergeDelayError(jumpShardsObservable, eventShardsObservable, yearsShardsObservable)
                .subscribeOn(Schedulers.computation())
                .sorted({ s1, s2 ->
                    if (s1.instant.isBefore(s2.instant)) {
                        return@sorted -1
                    } else if (s1.instant.isAfter(s2.instant)) {
                        return@sorted 1
                    } else {
                        return@sorted 0
                    }
                })
                .toList()
                .flatMapObservable { list -> Observable.create(applyShardPrefix(list)) }
    }

    private fun projectToYearShards(project: Project): ObservableOnSubscribe<TimelineShard> {
        return ObservableOnSubscribe {
            emitter ->
            try {

                val min = project.min.get(DateTimeFieldType.year())
                val max = project.max.get(DateTimeFieldType.year())

                for (y in min..max) {
                    val instant = DateTime("$y-01-01T00:00:00${getLocalTimeZone()}")
                    emitter.onNext(TimelineShard(instant, "$y", Color.GRAY, TimelineShard.ShardType.YEAR))
                }

                // complete
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    private fun travellerToShards(traveller: Traveller): Observable<TimelineShard> {
        return jumpRepository.getTravellerJumps(traveller)
                .map {
                    jump ->
                    return@map Pair<Jump, Traveller>(jump, traveller)
                }
                .toList()
                .flatMapObservable { list -> Observable.create(jumpsToSegments(list)) }
                .flatMap {
                    segment ->
                    val shardId = (segment.id and SHARD_ID_MASK) or SHARD_SEGMENT
                    val from = TimelineShard(segment.from, segment.legendFrom, segment.color, TimelineShard.ShardType.FIRST, shardId)
                    val dest = TimelineShard(segment.dest, segment.legendDest, segment.color, TimelineShard.ShardType.LAST, shardId)

                    return@flatMap Observable.just(from, dest)
                }
    }

    private fun applyShardPrefix(list: List<TimelineShard>): ObservableOnSubscribe<TimelineShard> {
        return ObservableOnSubscribe {
            emitter ->
            try {
                val prefix = ArrayList<TimelineShard?>()
                // TODO insert prefix on null positions
                for (shard in list) {
                    when (shard.type) {
                        TimelineShard.ShardType.YEAR -> {
                            shard.prefix.addAll(prefix)
                        }
                        TimelineShard.ShardType.SINGLE -> {
                            prefix.add(shard)
                            shard.prefix.addAll(prefix)
                            prefix.remove(shard)
                        }
                        TimelineShard.ShardType.LAST -> {
                            shard.prefix.addAll(prefix)
                            val index = prefix.indexOfFirst { s -> (s != null) && (s.id == shard.id) }
                            if (index < 0) {
                                Log.w("PREFIX", "Illegal index ! $prefix")
                            } else if (index == (prefix.size - 1)) {
                                prefix.removeAt(index)
                            } else {
                                prefix[index] = null
                            }
                        }
                        TimelineShard.ShardType.FIRST -> {
                            prefix.add(shard)
                            shard.prefix.addAll(prefix)
                        }
                    }
                    emitter.onNext(shard)
                }
                // complete
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }

    }

    private fun jumpsToSegments(list: List<Pair<Jump, Traveller>>): ObservableOnSubscribe<TimelineSegment> {
        return ObservableOnSubscribe {
            emitter ->
            try {

                var previous: Pair<Jump, Traveller>? = null
                for ((index, pair) in list.withIndex()) {
                    if (previous != null) {
                        val previousJump = previous.first
                        val traveller = previous.second
                        val currentJump = pair.first

                        val labelFrom: String
                        when (previousJump.id) {
                            Jump.ID_BIRTH -> labelFrom = "* ${traveller.name}"
                            Jump.ID_DEATH -> labelFrom = "† ${traveller.name}"
                            else -> labelFrom = "→ ${index - 1}"
                        }

                        val labelDest: String
                        when (currentJump.id) {
                            Jump.ID_BIRTH -> labelDest = "* ${traveller.name}"
                            Jump.ID_DEATH -> labelDest = "† ${traveller.name}"
                            else -> labelDest = "$index →"
                        }

                        val timelineSegment = TimelineSegment(labelFrom, labelDest, previousJump.destination, currentJump.from, traveller.color)
                        emitter.onNext(timelineSegment)
                    }

                    previous = pair
                }


                // complete
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }


    companion object {
        val SHARD_ID_MASK: Long = 0x0FFFFFFFFFFFFFFFL
        val SHARD_SEGMENT: Long = 0x1000000000000000L
        val SHARD_EVENT: Long = 0x2000000000000000L
    }
}