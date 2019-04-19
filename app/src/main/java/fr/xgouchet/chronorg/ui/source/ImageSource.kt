package fr.xgouchet.chronorg.ui.source

import android.widget.ImageView
import androidx.annotation.DrawableRes


sealed class ImageSource {

    abstract fun setImage(imageView: ImageView)

    // region Implementations

    data class Resource(@DrawableRes val id: Int)
        : ImageSource() {

        override fun setImage(imageView: ImageView) {
            imageView.setImageResource(id)
        }
    }

    // endregion
}


// region Extensions

//fun String.asImageSource(): ImageSource {
//    return ImageSource.Remote(this)
//}

fun Int.asImageSource(): ImageSource {
    return ImageSource.Resource(this)
}

fun ImageView.setImage(source: ImageSource) {
    source.setImage(this)
}

// endregion
