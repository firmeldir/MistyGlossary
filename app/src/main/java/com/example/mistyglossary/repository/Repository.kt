package com.example.mistyglossary.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.mistyglossary.database.WordDatabase
import com.example.mistyglossary.database.asDBE
import com.example.mistyglossary.database.asDomainModel
import com.example.mistyglossary.domain.DoneWord
import com.example.mistyglossary.network.TranslationService
import com.example.mistyglossary.viewmodels.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class Repository(private val wordDatabase: WordDatabase, private val translationNetwork: TranslationService, val language: String = "") {

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

    suspend fun updateWord(word: DoneWord) {
        withContext(Dispatchers.IO){
            wordDatabase.wordDao.updateWord(if(word.saved){1}else{0}, word.wordId)
        }
    }


    fun clear() {
        wordDatabase.wordDao.clear()
    }

    suspend fun processRequest(requestString: String, _curResult: MutableLiveData<String>,_onError : MutableLiveData<Boolean>, path: String, title: String)
    {
        val deffered = translationNetwork.getTranslation(path, requestString)

        try {
            _curResult.value = deffered.await().contents?.translated.toString()

            withContext(Dispatchers.IO){
                insertWord(DoneWord(requestString, _curResult.value.toString(), title))
            }
        }
        catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            _onError.value = true
        }
    }

}