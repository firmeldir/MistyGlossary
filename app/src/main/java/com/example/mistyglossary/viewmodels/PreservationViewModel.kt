package com.example.mistyglossary.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mistyglossary.MistyApplication
import com.example.mistyglossary.database.getDatabase
import com.example.mistyglossary.domain.DoneWord
import com.example.mistyglossary.injection.DaggerFragmentComponent
import com.example.mistyglossary.injection.module.RepositoryModule
import com.example.mistyglossary.repository.Repository
import kotlinx.coroutines.*
import javax.inject.Inject

class PreservationViewModel(application: Application) : AndroidViewModel(application)
{
    //Coroutines
    private val coroutineJob = Job()

    private val coroutineScope = CoroutineScope(coroutineJob + Dispatchers.Main)

    //Repository will rule them all
    val repository : Repository

    init {
        val fragmentComponent = DaggerFragmentComponent.builder()
            .mainIOComponent(MistyApplication.get(application).mainIOComponent)
            .repositoryModule(RepositoryModule(application.applicationContext, "")).build()
        repository = fragmentComponent.getRepository()
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