package com.example.mistyglossary.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mistyglossary.database.getDatabase
import com.example.mistyglossary.domain.DoneWord
import com.example.mistyglossary.repository.Repository
import kotlinx.coroutines.*

class PreservationViewModel(application: Application) : AndroidViewModel(application)
{
    //Coroutines
    private val coroutineJob = Job()

    private val coroutineScope = CoroutineScope(coroutineJob + Dispatchers.Main)

    //Repository will rule them all
    private val repository : Repository

    init {
        repository = Repository(getDatabase(application))
    }

    val savedWords: LiveData<List<DoneWord>>
        get() = repository.savedWords

    fun updateWord(doneWord: DoneWord) {
        coroutineScope.launch {
            withContext(Dispatchers.IO){
                repository.updateWord(doneWord)
            }
        }
    }

    class PreservationViewModelFactory(private val application: Application) : ViewModelProvider.Factory
    {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PreservationViewModel::class.java)) {
                return PreservationViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}