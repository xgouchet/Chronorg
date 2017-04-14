package fr.xgouchet.khronorg.feature.samples

import android.graphics.Color
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import fr.xgouchet.khronorg.commons.query.QueryBuilder
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.feature.events.Event
import fr.xgouchet.khronorg.feature.jumps.Jump
import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.feature.projects.ProjectNavigator
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.provider.KhronorgSchema
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

/**
 * @author Xavier F. Gouchet
 */
class BackToTheFutureSample(val kodein: Kodein, val navigator: ProjectNavigator) {

    fun addSampleProject() {
        val projectRepository = kodein.instance<BaseRepository<Project>>()

        val jumpRepository = kodein.instance<BaseRepository<Jump>>()

        val bttf = Project(name = "Back to the Future",
                min = DateTime("1865-01-01T12:00:00$TZ"),
                max = DateTime("2019-12-31T12:00:00$TZ"))

        projectRepository.save(bttf)
                .observeOn(Schedulers.io())
                .doOnComplete {
                    projectRepository.getWhere(QueryBuilder.where { equals(KhronorgSchema.COL_NAME, bttf.name) })
                            .observeOn(Schedulers.io())
                            .doOnNext {
                                project ->
                                addSampleEvents(project)
                                addSampleTravellers(project)
                                navigator.goToItemDetails(project)
                            }
                            .subscribe()


                }.subscribe()


    }

    private fun addSampleTravellers(project: Project) {

        val jumpRepository = kodein.instance<BaseRepository<Jump>>()

        val jumpsCreator = BehaviorSubject.create<Jump>()

        jumpsCreator.observeOn(Schedulers.io())
                .flatMap {
                    jump ->
                    jumpRepository.save(jump)
                }
                .subscribe()


        addMarty(project, jumpsCreator)
        addDoc(project, jumpsCreator)
        addBiff(project, jumpsCreator)
    }

    private fun addMarty(project: Project, jumpsCreator: BehaviorSubject<Jump>) {

        val travellerRepository = kodein.instance<BaseRepository<Traveller>>()
        val marty = Traveller(
                projectId = project.id,
                name = "Marty Mc Fly",
                birth = DateTime("1968-06-20T13:22:00$TZ"),
                death = DateTime("2040-08-11T05:07:00$TZ"),
                color = COLOR_MARTY)
        travellerRepository.save(marty)
                .observeOn(Schedulers.io())
                .doOnComplete {
                    travellerRepository.getWhere(QueryBuilder.where { equals(KhronorgSchema.COL_NAME, marty.name) })
                            .observeOn(Schedulers.io())
                            .doOnNext {
                                traveller ->
                                addMartyJumps(traveller, jumpsCreator)
                            }
                            .subscribe()


                }.subscribe()
    }

    private fun addDoc(project: Project, jumpsCreator: BehaviorSubject<Jump>) {

        val travellerRepository = kodein.instance<BaseRepository<Traveller>>()
        val doc = Traveller(
                projectId = project.id,
                name = "Doc Brown",
                birth = DateTime("1920-10-22T04:08:15$TZ"),
                death = DateTime("2030-03-29T10:00:00$TZ"),
                color = COLOR_DOC)
        travellerRepository.save(doc)
                .observeOn(Schedulers.io())
                .doOnComplete {
                    travellerRepository.getWhere(QueryBuilder.where { equals(KhronorgSchema.COL_NAME, doc.name) })
                            .observeOn(Schedulers.io())
                            .doOnNext {
                                traveller ->
                                addDocJumps(traveller, jumpsCreator)
                            }
                            .subscribe()


                }.subscribe()
    }

    private fun addBiff(project: Project, jumpsCreator: BehaviorSubject<Jump>) {

        val travellerRepository = kodein.instance<BaseRepository<Traveller>>()
        val biff = Traveller(
                projectId = project.id,
                name = "Biff Tannen",
                birth = DateTime("1937-03-28T04:08:15$TZ"),
                death = DateTime("2018-01-25T10:00:00$TZ"),
                color = COLOR_BIFF)
        travellerRepository.save(biff)
                .observeOn(Schedulers.io())
                .doOnComplete {
                    travellerRepository.getWhere(QueryBuilder.where { equals(KhronorgSchema.COL_NAME, biff.name) })
                            .observeOn(Schedulers.io())
                            .doOnNext {
                                traveller ->
                                addBiffJumps(traveller, jumpsCreator)
                            }
                            .subscribe()


                }.subscribe()
    }

    private fun addBiffJumps(biff: Traveller, jumpsCreator: BehaviorSubject<Jump>) {
        jumpsCreator.onNext(Jump(
                travellerId = biff.id,
                from = HIJACK_2015_DEPARTURE,
                dest = HIJACK_1955_LANDING))

        jumpsCreator.onNext(Jump(
                travellerId = biff.id,
                from = HIJACK_RETURN_1955_DEPARTURE,
                dest = HIJACK_RETURN_2015_LANDING))
    }

    private fun addDocJumps(doc: Traveller, jumpsCreator: BehaviorSubject<Jump>) {

        jumpsCreator.onNext(Jump(
                travellerId = doc.id,
                from = LYON_ESTATE_1985_DEPARTURE,
                dest = LYON_ESTATE_2015_LANDING))

        jumpsCreator.onNext(Jump(
                travellerId = doc.id,
                from = HILLDALE_2015_DEPARTURE,
                dest = HILLDALE_1985_LANDING))

        jumpsCreator.onNext(Jump(
                travellerId = doc.id,
                from = HELL_1985_DEPARTURE,
                dest = HILL_VALLEY_1955_LANDING))

        jumpsCreator.onNext(Jump(
                travellerId = doc.id,
                from = LIGHTNING_1955_DEPARTURE,
                dest = LIGHTNING_1885_LANDING))

        jumpsCreator.onNext(Jump(
                travellerId = doc.id,
                from = DOC_TRAIN_DEPARTURE,
                dest = DOC_TRAIN_LANDING))
    }

    private fun addMartyJumps(marty: Traveller, jumpsCreator: BehaviorSubject<Jump>) {
        jumpsCreator.onNext(Jump(
                travellerId = marty.id,
                from = TWINE_PINE_MALL_1985_DEPARTURE,
                dest = PEABODY_FARM_1955_LANDING))

        jumpsCreator.onNext(Jump(
                travellerId = marty.id,
                from = CLOCK_TOWER_1955_DEPARTURE,
                dest = CLOCK_TOWER_1985_LANDING))

        jumpsCreator.onNext(Jump(
                travellerId = marty.id,
                from = LYON_ESTATE_1985_DEPARTURE,
                dest = LYON_ESTATE_2015_LANDING))

        jumpsCreator.onNext(Jump(
                travellerId = marty.id,
                from = HILLDALE_2015_DEPARTURE,
                dest = HILLDALE_1985_LANDING))

        jumpsCreator.onNext(Jump(
                travellerId = marty.id,
                from = HELL_1985_DEPARTURE,
                dest = HILL_VALLEY_1955_LANDING))

        jumpsCreator.onNext(Jump(
                travellerId = marty.id,
                from = DESERT_1955_DEPARTURE,
                dest = DESERT_1885_LANDING))

        jumpsCreator.onNext(Jump(
                travellerId = marty.id,
                from = SHONASH_RAVINE_1885_DEPARTURE,
                dest = EASTWOOD_RAVINE_1985_LANDING))
    }

    private fun addSampleEvents(project: Project) {
        val eventRepository = kodein.instance<BaseRepository<Event>>()

        val eventsCreator = BehaviorSubject.create<Event>()

        eventsCreator.observeOn(Schedulers.io())
                .flatMap {
                    event ->
                    eventRepository.save(event)
                }
                .subscribe()

        eventsCreator.onNext(Event(
                projectId = project.id,
                name = "Hill Valley Foundation",
                instant = DateTime("1865-09-05T12:00:00$TZ"),
                color = COLOR_HILL_VALLEY))

        eventsCreator.onNext(Event(
                projectId = project.id,
                name = "Lightning strikes the clock tower",
                instant = CLOCK_TOWER_1955_DEPARTURE,
                color = COLOR_HILL_VALLEY))

        eventsCreator.onNext(Event(
                projectId = project.id,
                name = "Doc Brown slips off his toilet",
                instant = DateTime("1955-11-5T15:33:00$TZ"),
                color = COLOR_DOC))

        eventsCreator.onNext(Event(
                projectId = project.id,
                name = "Doc Brown shot by Lybians",
                instant = DateTime("1985-10-26T01:33:00$TZ"),
                color = COLOR_DOC))

        eventsCreator.onNext(Event(
                projectId = project.id,
                name = "Marty plays guitar",
                instant = DateTime("1985-10-25T08:23:00$TZ"),
                color = COLOR_MARTY))
    }

    companion object {
        val TZ = "-08:00"
        val COLOR_HILL_VALLEY = Color.rgb(161, 136, 127)
        val COLOR_DOC = Color.rgb(144, 164, 174)
        val COLOR_BIFF = Color.rgb(67,160,71)
        val COLOR_MARTY = Color.rgb(211, 47, 47)

        val TWINE_PINE_MALL_1985_DEPARTURE = DateTime("1985-10-26T01:35:00$TZ")
        val PEABODY_FARM_1955_LANDING = DateTime("1955-11-05T06:15:00$TZ")
        val CLOCK_TOWER_1955_DEPARTURE = DateTime("1955-11-12T22:04:00$TZ")
        val CLOCK_TOWER_1985_LANDING = DateTime("1985-10-26T01:24:00$TZ")
        val LYON_ESTATE_1985_DEPARTURE = DateTime("1985-10-26T11:00:00$TZ")
        val LYON_ESTATE_2015_LANDING = DateTime("2015-10-21T16:29:00$TZ")

        val HIJACK_2015_DEPARTURE = DateTime("2015-10-21T19:07:00$TZ")
        val HIJACK_1955_LANDING = DateTime("1955-11-12T07:00:00$TZ")

        val HIJACK_RETURN_1955_DEPARTURE = DateTime("1955-11-12T18:38:00$TZ")
        val HIJACK_RETURN_2015_LANDING = DateTime("2015-10-21T19:23:00$TZ")

        val HILLDALE_2015_DEPARTURE = DateTime("2015-10-21T19:28:00$TZ")
        val HILLDALE_1985_LANDING = DateTime("1985-10-26T09:00:00$TZ")

        val HELL_1985_DEPARTURE = DateTime("1985-10-27T02:42:00$TZ")
        val HILL_VALLEY_1955_LANDING = DateTime("1955-11-12T21:44:00$TZ")

        val LIGHTNING_1955_DEPARTURE = DateTime(1955, 11, 12, 21, 44, 0, 0, DateTimeZone.UTC)
        val LIGHTNING_1885_LANDING = DateTime(1885, 1, 1, 0, 0, 0, 0, DateTimeZone.UTC)

        val DESERT_1955_DEPARTURE = DateTime("1955-11-16T10:00:00$TZ")
        val DESERT_1885_LANDING = DateTime("1885-09-02T08:00:00$TZ")

        val SHONASH_RAVINE_1885_DEPARTURE = DateTime("1885-09-07T09:00:00$TZ")
        val EASTWOOD_RAVINE_1985_LANDING = DateTime("1985-10-27T11:00:00$TZ")


        val DOC_TRAIN_DEPARTURE = DateTime("1895-04-20T18:00:00$TZ")
        val DOC_TRAIN_LANDING = DateTime("1985-10-27T13:00:00$TZ")
    }

}