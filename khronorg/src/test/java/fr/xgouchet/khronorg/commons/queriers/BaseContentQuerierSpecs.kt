package fr.xgouchet.khronorg.commons.queriers

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import com.nhaarman.mockito_kotlin.*
import fr.xgouchet.khronorg.commons.ioproviders.IOProvider
import io.reactivex.functions.Consumer
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

/**
 * @author Xavier F. Gouchet
 */
@RunWith(JUnitPlatform::class)
class BaseContentQuerierSpecs : Spek({

    describe("a base content querier") {
        val provider: IOProvider<String> = mock()
        val getId = String::length
        val uri = Uri.parse("fake://khronorg/test")

        val querier = StringContentQuerier(provider, getId, uri)


        on("query all") {
            val cursor: Cursor = mock{
                on {count} doReturn 3

            }
            val resolver: ContentResolver = mock<ContentResolver> {
                on { query(any<Uri>(), any<Array<out String>>(), any<String>(), any<Array<out String>>(), any<String>()) } doReturn cursor
            }

            val consumer: Consumer<String> = mock()

            querier.queryAll(resolver, consumer)

            it("should return the result of adding the first number to the second number") {
                verify(resolver).query(uri, null, null, null, null)
                verify(consumer).accept("Foo")
            }
        }
    }
})


class StringContentQuerier(ioProvider: IOProvider<String>,
                           val delegate: (String) -> Int,
                           override val uri: Uri)
    : BaseContentQuerier<String>(ioProvider) {

    override fun getId(item: String): Int = delegate(item)

}
