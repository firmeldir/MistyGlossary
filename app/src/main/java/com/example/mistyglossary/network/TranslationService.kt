package com.example.mistyglossary.network

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://api.funtranslations.com/translate/"

interface TranslationService {
    @GET("{language}")
    fun getTranslation(@Path("language") language: String, @Query("text") text: String ): Deferred<translateResponse>
}

