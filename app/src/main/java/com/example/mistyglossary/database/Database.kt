package com.example.mistyglossary.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private lateinit var INSTANCE: WordDatabase

@Database(entities = [DBEDoneWord::class], version = 1)
abstract class WordDatabase: RoomDatabase() {
    abstract val wordDao: WordsDao
}



fun getDatabase(context: Context): WordDatabase {
    synchronized(WordDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                WordDatabase::class.java,
                "words").build()
        }
    }
    return INSTANCE
}