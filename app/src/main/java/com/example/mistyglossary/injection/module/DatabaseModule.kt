package com.example.mistyglossary.injection.module

import android.content.Context
import com.example.mistyglossary.database.WordDatabase
import com.example.mistyglossary.database.WordsDao
import com.example.mistyglossary.database.getDatabase
import com.example.mistyglossary.injection.MainIOScope
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module(includes = [ContextModule::class])
class DatabaseModule {

    @MainIOScope
    @Provides
    fun wordsDao(database: WordDatabase): WordsDao = database.wordDao

    @MainIOScope
    @Provides
    fun wordDatabase(@Named("application_context") context: Context) : WordDatabase = getDatabase(context)
}