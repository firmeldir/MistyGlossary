package com.example.mistyglossary.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordsDao
{
    @Query("select * from words where saved = 'true'")
    fun getSavedWords() : LiveData<List<DBEDoneWord>>

    @Query("select * from words where lang = :lan")
    fun getLanWords(lan: String) : LiveData<List<DBEDoneWord>>

    @Query("select * from words where engWord = :engWord limit 1")
    fun findWord(engWord: String) : DBEDoneWord?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(word: DBEDoneWord)

    @Query("delete from words")
    fun clear()
}
