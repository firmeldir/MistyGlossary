package com.example.mistyglossary.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordsDao
{
    @Query("select * from words where saved = 1")
    fun getSavedWords() : LiveData<List<DBEDoneWord>>

    @Query("select * from words where lang = :lan")
    fun getLanWords(lan: String) : LiveData<List<DBEDoneWord>>

    @Query("select * from words where engWord = :engWord limit 1")
    fun findWord(engWord: String) : DBEDoneWord?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(word: DBEDoneWord)

    @Query("update words set saved=:saved where wordId = :id")
    fun updateWord(saved: Int, id: Int)

    @Query("delete from words")
    fun clear()
}
