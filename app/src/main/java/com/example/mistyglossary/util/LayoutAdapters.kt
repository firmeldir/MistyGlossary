package com.example.mistyglossary.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.mistyglossary.domain.MistyLanguage


@BindingAdapter("setResImage")
fun setResImage(imageView: ImageView, item: Int)
{
    Glide.with(imageView.context).load(imageView.context.getDrawable(item)).into(imageView)
}

@BindingAdapter("setBackToImage")
fun setBackToImage(imageView: ImageView, item: Int)
{
    imageView.setBackgroundResource(item)
}

@BindingAdapter("setResImage")
fun setResImage(imageView: ImageView, item: MistyLanguage?)
{
    item?.let {
        Glide.with(imageView.context)
            .load(imageView.context.getDrawable(item.imageResource))
            .into(imageView)
    }
}



