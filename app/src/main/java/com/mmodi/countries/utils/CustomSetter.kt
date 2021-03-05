package com.mmodi.countries.utils

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

@BindingAdapter("loadImage")
fun loadImageWithCompleteUrl(view: ImageView, url: String?) {
    url?.let {
        GlideToVectorYou
            .init()
            .with(view.context)
            .load(Uri.parse(it), view)
    }
}