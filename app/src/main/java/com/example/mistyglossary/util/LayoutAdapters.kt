package com.example.mistyglossary.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.mistyglossary.domain.MistyLanguage


@BindingAdapter("setCusImage")
fun ImageView.setTitleImage(item: MistyLanguage)
{
    Glide.with(context).load(item.imageResource).into(this)
}