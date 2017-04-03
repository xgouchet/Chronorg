package fr.xgouchet.khronorg.feature.jumps

import android.content.Context
import fr.xgouchet.khronorg.commons.ioproviders.IOProvider
import fr.xgouchet.khronorg.commons.query.QueryBuilder
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.data.models.Direction
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.provider.KhronorgSchema
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.functions.Consumer
import org.joda.time.Duration
import org.joda.time.Interval

/**
 * @author Xavier F. Gouchet
 */
class JumpRepository(context: Context, provider: IOProvider<Jump>) : BaseRepository<Jump>(context, provider) {

    fun getTravellerJumps(traveller: Traveller): Observable<Jump> {
        val source = ObservableOnSubscribe<Jump> {
            emitter ->
            try {
                // Birth
                val birth = Jump(id = Jump.ID_BIRTH,
                        travellerId = traveller.id,
                        from = traveller.birth,
                        delay = Interval(traveller.birth, Duration.ZERO),
                        direction = Direction.FUTURE)
                emitter.onNext(birth)

                // User defined jumps
                val query = QueryBuilder.where { equals(KhronorgSchema.COL_TRAVELLER_ID, traveller.id.toString()) }
                val contentResolver = context.contentResolver
                provider.querier.queryWhere(contentResolver, query, Consumer { t -> emitter.onNext(t) })

                // Death
                val death = Jump(id = Jump.ID_DEATH,
                        travellerId = traveller.id,
                        from = traveller.death,
                        delay = Interval(traveller.death, Duration.ZERO),
                        direction = Direction.FUTURE)
                emitter.onNext(death)

                // complete
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }

        return Observable.create(source)
    }

}