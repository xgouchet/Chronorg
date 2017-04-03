package fr.xgouchet.khronorg.feature.timeline

import fr.xgouchet.khronorg.commons.query.QueryBuilder
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.feature.events.Event
import fr.xgouchet.khronorg.feature.jumps.Jump
import fr.xgouchet.khronorg.feature.jumps.JumpRepository
import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.provider.KhronorgSchema
import fr.xgouchet.khronorg.ui.presenters.BaseListPresenter
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * @author Xavier F. Gouchet
 */
class ShardListPresenter(val travellerRepository: BaseRepository<Traveller>,
                         val jumpRepository: JumpRepository,
                         val eventRepository: BaseRepository<Event>,
                         val project: Project,
                         navigator: ShardNavigator)
    : BaseListPresenter<TimelineShard>(navigator) {


    override fun getItemsObservable(): Observable<TimelineShard> {
        val query = QueryBuilder.where {
            equals(KhronorgSchema.COL_PROJECT_ID, project.id.toString())
        }
        val eventShardsObservable: Observable<TimelineShard> = eventRepository.getWhere(query)
                .subscribeOn(Schedulers.io())
                .map {
                    event ->
                    TimelineShard(event.instant, event.name, event.color)
                }

        val jumpShardsObservable: Observable<TimelineShard> = travellerRepository.getWhere(query)
                .subscribeOn(Schedulers.io())
                .flatMap { traveller ->
                    return@flatMap jumpRepository.getTravellerJumps(traveller)
                            .map { jump -> Pair<Jump, Traveller>(jump, traveller) }
                }
                .toList()
                .flatMapObservable { list -> Observable.create(jumpsToSegments(list)) }
                .flatMap {
                    segment ->
                    val from = TimelineShard(segment.from, segment.legendFrom, segment.color, TimelineShard.ShardType.FIRST, segment.id)
                    val dest = TimelineShard(segment.dest, segment.legendDest, segment.color, TimelineShard.ShardType.LAST, segment.id)
                    return@flatMap Observable.just(from, dest)
                }

        return Observable.mergeDelayError(jumpShardsObservable, eventShardsObservable)
                .subscribeOn(Schedulers.computation())
                .sorted { s1, s2 ->
                    if (s1.instant.isBefore(s2.instant)) {
                        return@sorted -1
                    } else if (s1.instant.isAfter(s2.instant)) {
                        return@sorted 1
                    } else {
                        return@sorted 0
                    }
                }
                .toList()
                .flatMapObservable { list -> Observable.create(applyShardPrefix(list)) }
    }

    private fun applyShardPrefix(list: List<TimelineShard>): ObservableOnSubscribe<TimelineShard> {
        return ObservableOnSubscribe {
            emitter ->
            try {
                val prefix = ArrayList<TimelineShard?>()

                // TODO replace by null instead of remove
                for (shard in list) {
                    when (shard.type) {
                        TimelineShard.ShardType.SINGLE -> {
                            prefix.add(shard)
                            shard.prefix.addAll(prefix)
                            prefix.remove(shard)
                        }
                        TimelineShard.ShardType.LAST -> {
                            shard.prefix.addAll(prefix)
                            val index = prefix.indexOfFirst { s -> (s != null) && (s.id == shard.id) }
                            if (index < 0) {
                                throw IllegalStateException("WTF ‽")
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

                        emitter.onNext(TimelineSegment(labelFrom, labelDest, previousJump.destination, currentJump.from, traveller.color))
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


}