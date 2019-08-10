package com.example.mistyglossary.injection.module

import com.example.mistyglossary.injection.MainIOScope
import com.example.mistyglossary.network.BASE_URL
import com.example.mistyglossary.network.TranslationService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class ServicesModule {

    @MainIOScope
    @Provides
    fun translationService(retrofit: Retrofit): TranslationService = retrofit.create(TranslationService::class.java)

    @MainIOScope
    @Provides
    fun retrofit(moshiConverterFactory: MoshiConverterFactory, coroutineCallAdapterFactory: CoroutineCallAdapterFactory): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(moshiConverterFactory)
        .addCallAdapterFactory(coroutineCallAdapterFactory)
        .build()

    @MainIOScope
    @Provides
    fun coroutineCallAdapterFactory(): CoroutineCallAdapterFactory = CoroutineCallAdapterFactory()

    @MainIOScope
    @Provides
    fun moshiConverterFactory(moshi: Moshi) : MoshiConverterFactory = MoshiConverterFactory.create(moshi)

    @MainIOScope
    @Provides
    fun moshi(kotlinJsonAdapterFactory: KotlinJsonAdapterFactory): Moshi = Moshi.Builder()
        .add(kotlinJsonAdapterFactory)
        .build()

    @MainIOScope
    @Provides
    fun kotlinJsonFactory(): KotlinJsonAdapterFactory = KotlinJsonAdapterFactory()
}