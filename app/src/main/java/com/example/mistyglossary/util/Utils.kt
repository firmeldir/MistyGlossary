package com.example.mistyglossary.util

import android.content.Context
import android.util.Log
import com.example.mistyglossary.R
import com.example.mistyglossary.domain.MistyLanguage

fun initializeLanguages(context: Context) : List<MistyLanguage>
{
    var mistyLanguage = mutableListOf<MistyLanguage>()

    val titles = context.resources.getStringArray(R.array.lan_title)
    val info = context.resources.getStringArray(R.array.lan_info)
    val path = context.resources.getStringArray(R.array.lan_path)
    val images = context.resources.obtainTypedArray(R.array.lan_image)


    for((index , item) in titles.withIndex())
    {
        mistyLanguage.add(MistyLanguage(titles[index], info[index], path[index], images.getResourceId(index, 0)))
        Log.i("TAG", titles[index])
    }

    images.recycle()

    return mistyLanguage
}