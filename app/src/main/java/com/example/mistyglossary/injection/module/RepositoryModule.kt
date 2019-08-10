package com.example.mistyglossary.injection.module

import android.content.Context
import com.example.mistyglossary.database.getDatabase
import com.example.mistyglossary.injection.FragmentScope
import com.example.mistyglossary.network.TranslationService
import com.example.mistyglossary.repository.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module(includes = [ContextModule::class])
class RepositoryModule(private val context: Context, private val lan: String){

    @FragmentScope
    @Provides
    fun repository(translationService: TranslationService,
                   @Named("model_context") context: Context): Repository = Repository(getDatabase(context), translationService  , lan)

    @Named("model_context")
    @FragmentScope
    @Provides
    fun context():Context = context
}