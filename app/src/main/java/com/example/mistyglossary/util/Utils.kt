package com.example.mistyglossary.util

import android.content.Context
import android.util.Log
import com.example.mistyglossary.R
import com.example.mistyglossary.domain.MistyLanguage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun initializeLanguages(context: Context) : List<MistyLanguage>
{
    var mistyLanguage = mutableListOf<MistyLanguage>()

    val titles = context.resources.getStringArray(R.array.lan_title)
    val info = context.resources.getStringArray(R.array.lan_info)
    val path = context.resources.getStringArray(R.array.lan_path)

    val images = context.resources.obtainTypedArray(R.array.lan_image)
    val back = context.resources.obtainTypedArray(R.array.lan_back)


    for((index , item) in titles.withIndex())
    {
        mistyLanguage.add(MistyLanguage(index ,item, info[index], path[index], images.getResourceId(index, 0),back.getResourceId(index, 0) ))
        Log.i("TAG", titles[index])
    }

    images.recycle()
    back.recycle()

    return mistyLanguage
}

fun initializeLanguage(id: Int, context: Context) : MistyLanguage
{
    lateinit var mistyLanguage : MistyLanguage

    //withContext(Dispatchers.IO)
    //{
        val images = context.resources.obtainTypedArray(R.array.lan_image)
        val back = context.resources.obtainTypedArray(R.array.lan_back)

        mistyLanguage =  MistyLanguage(id,
            context.resources.getStringArray(R.array.lan_title)[id],
            context.resources.getStringArray(R.array.lan_info)[id],
            context.resources.getStringArray(R.array.lan_path)[id],
            images.getResourceId(id, 0) ,
            back.getResourceId(id, 0)
        )

        images.recycle()
        back.recycle()

    //}

    return mistyLanguage
}