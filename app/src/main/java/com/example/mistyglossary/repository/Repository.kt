package com.example.mistyglossary.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.mistyglossary.database.WordDatabase
import com.example.mistyglossary.database.asDBE
import com.example.mistyglossary.database.asDomainModel
import com.example.mistyglossary.domain.DoneWord

class Repository(private val wordDatabase: WordDatabase, val language: String = "") {

    val lanWords : LiveData<List<DoneWord>> by lazy {
        Transformations.map(wordDatabase.wordDao.getLanWords(language)) {
            it.asDomainModel()
        }
    }

    val savedWords : LiveData<List<DoneWord>> by lazy {
        Transformations.map(wordDatabase.wordDao.getSavedWords()){
            it.asDomainModel()
        }
    }

    fun insertWord(word: DoneWord) {
        wordDatabase.wordDao.insertWord(word.asDBE())
    }

    fun findWord(word: String) : String? = wordDatabase.wordDao.findWord(word)?.transWord


    fun clear() {
        wordDatabase.wordDao.clear()
    }

}