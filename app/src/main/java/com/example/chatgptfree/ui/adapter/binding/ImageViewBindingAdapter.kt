package com.example.chatgptfree.ui.adapter.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.chatgptfree.utils.loadImage

@BindingAdapter("app:image")
fun loadImage(imageView: ImageView, url: String?) {
    imageView.loadImage(url)
}
