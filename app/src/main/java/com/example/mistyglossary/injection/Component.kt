package com.example.mistyglossary.injection

import androidx.lifecycle.ViewModel
import com.example.mistyglossary.database.WordsDao
import com.example.mistyglossary.injection.module.ContextModule
import com.example.mistyglossary.injection.module.DatabaseModule
import com.example.mistyglossary.injection.module.RepositoryModule
import com.example.mistyglossary.injection.module.ServicesModule
import com.example.mistyglossary.network.TranslationService
import com.example.mistyglossary.repository.Repository
import dagger.Component
import javax.inject.Scope


@Scope
@Retention(AnnotationRetention.BINARY)
annotation class MainIOScope


@MainIOScope
@Component(modules = [DatabaseModule::class, ServicesModule::class, ContextModule::class])
interface MainIOComponent{

    fun getWordsDao(): WordsDao

    fun getTranslationService(): TranslationService
}

@Scope
@Retention(AnnotationRetention.BINARY)
annotation class FragmentScope

@FragmentScope
@Component(modules = [RepositoryModule::class],dependencies = [MainIOComponent::class])
interface FragmentComponent{

    fun injectViewModel(viewModel: ViewModel)

    fun getRepository(): Repository
}