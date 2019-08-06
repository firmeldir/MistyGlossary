package com.example.mistyglossary.util

import android.animation.AnimatorSet
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.View
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import androidx.databinding.adapters.ImageViewBindingAdapter.setImageDrawable
import android.R.attr.closeIcon
import android.util.Log
import android.widget.ImageView


class NavClickListener(val context: Context,
                       val view: View,
                       val openDrawable: Drawable? = null,
                       val closeDrawable: Drawable? = null,
                       val interpolat: android.view.animation.Interpolator? = null) : View.OnClickListener
{
    private val height: Int
    private var isOpen: Boolean = false
    private val animatorSet = AnimatorSet()

    init {
        height = if(context is Activity) {
            val dispayMetrics = DisplayMetrics()
            context.windowManager.defaultDisplay.getMetrics(dispayMetrics)
            dispayMetrics.heightPixels
        } else 0
    }


    private fun iconManage(v: View)
    {
        openDrawable?.let {
            if (isOpen) {
                if(v is ImageView) v.setImageDrawable(closeDrawable)
            } else {
                if(v is ImageView) v.setImageDrawable(openDrawable)
            }
        }
    }

    override fun onClick(v: View?) {
        isOpen = !isOpen

        animatorSet.removeAllListeners()
        animatorSet.end()
        animatorSet.cancel()

        v?.let { iconManage(v) }

        val translateY: Float = (height -
                context.resources.getDimensionPixelSize(com.example.mistyglossary.R.dimen.backdrop_height)).toFloat()

        val animator = ObjectAnimator.ofFloat(view, "translationY", if(isOpen){ translateY }else{0f}).apply {
            duration = 500
            interpolator = interpolat
        }

        animatorSet.play(animator)
        animatorSet.start()
    }
}