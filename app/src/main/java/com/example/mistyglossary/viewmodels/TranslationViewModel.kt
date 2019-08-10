package com.example.mistyglossary.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.mistyglossary.MistyApplication
import com.example.mistyglossary.domain.DoneWord
import com.example.mistyglossary.domain.MistyLanguage
import com.example.mistyglossary.injection.DaggerFragmentComponent
import com.example.mistyglossary.injection.module.RepositoryModule
import com.example.mistyglossary.repository.Repository
import com.example.mistyglossary.util.initializeLanguage
import kotlinx.coroutines.*
import javax.inject.Inject


const val TAG = "TAG"

class TranslationViewModel(curLanId: Int, application: Application) : AndroidViewModel(application)
{

    //Current lan <- LanList
    private val _curLanguage = MutableLiveData<MistyLanguage>()

    val curLanguage : LiveData<MistyLanguage>
        get() = _curLanguage

    //Repository will rule them all
    val repository : Repository

    //DoneWords From DB
    val lanWords : LiveData<List<DoneWord>>
        get() = repository.lanWords

    //Result From REST
    private val _curResult = MutableLiveData<String>()

    val curResult : LiveData<String>
        get() = _curResult

    //Network Error Catcher
    private val _onError : MutableLiveData<Boolean> = MutableLiveData()
    val onError : LiveData<Boolean>
        get() = _onError

    fun onErrorReact(){_onError.value = false}

    //Coroutines
    private val coroutineJob = Job()

    private val coroutineScope = CoroutineScope(coroutineJob + Dispatchers.Main)



    init {
        _curLanguage.value = initializeLanguage(curLanId,getApplication())
        _onError.value = false

        val fragmentComponent = DaggerFragmentComponent.builder()
            .mainIOComponent(MistyApplication.get(application).mainIOComponent)
            .repositoryModule(RepositoryModule(application.applicationContext, curLanguage.value!!.title)).build()
        repository = fragmentComponent.getRepository()
    }


    fun reactRequest(requestString: String)
    {
        coroutineScope.launch {
            repository.processRequest(requestString, _curResult, _onError, _curLanguage.value!!.path,_curLanguage.value!!.title)
        }
    }

    fun updateWord(doneWord: DoneWord) {
        coroutineScope.launch {
            repository.updateWord(doneWord)
        }
    }

    class TranslationViewModelFactory(private val lanId: Int, private val application: Application) : ViewModelProvider.Factory
    {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TranslationViewModel::class.java)) {
                return TranslationViewModel(lanId, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

    override fun onCleared() {
        super.onCleared()
        coroutineJob.cancel()
    }
}