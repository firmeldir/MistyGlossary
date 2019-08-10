package com.example.mistyglossary

import android.app.Application
import com.example.mistyglossary.injection.DaggerMainIOComponent
import com.example.mistyglossary.injection.MainIOComponent
import com.example.mistyglossary.injection.module.ContextModule

class MistyApplication : Application(){

    lateinit var mainIOComponent: MainIOComponent


    companion object{
        fun get(application: Application): MistyApplication = application as MistyApplication
    }


    override fun onCreate() {
        super.onCreate()

        mainIOComponent = DaggerMainIOComponent.builder().contextModule(ContextModule(this)).build()
    }


}