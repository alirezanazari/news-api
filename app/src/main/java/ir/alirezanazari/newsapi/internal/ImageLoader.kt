package ir.alirezanazari.newsapi.internal

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy


class ImageLoader {

    fun load(iv: ImageView, url: String, placeHolder: Int) {
        Glide.with(iv.context)
            .load(url)
            .placeholder(placeHolder)
            .error(placeHolder)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(iv)

    }
}