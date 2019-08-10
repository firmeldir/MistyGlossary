package com.example.mistyglossary.injection.module

import android.content.Context
import com.example.mistyglossary.injection.MainIOScope
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ContextModule(val context: Context){

    @Named("application_context")
    @MainIOScope
    @Provides
    fun context():Context = context
}