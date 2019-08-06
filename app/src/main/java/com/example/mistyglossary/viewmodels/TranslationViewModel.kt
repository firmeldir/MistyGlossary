package com.example.mistyglossary.viewmodels

import android.app.Application
import android.os.Build.VERSION_CODES.M
import android.util.Log
import androidx.lifecycle.*
import com.example.mistyglossary.database.getDatabase
import com.example.mistyglossary.domain.DoneWord
import com.example.mistyglossary.domain.MistyLanguage
import com.example.mistyglossary.network.TraslationNetwork
import com.example.mistyglossary.network.translateResponse
import com.example.mistyglossary.repository.Repository
import com.example.mistyglossary.util.initializeLanguage
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import javax.security.auth.callback.Callback

const val TAG = "TAG"

class TranslationViewModel(val curLanId: Int, application: Application) : AndroidViewModel(application)
{

    //Current lan <- LanList
    private val _curLanguage = MutableLiveData<MistyLanguage>()

    val curLanguage : LiveData<MistyLanguage>
        get() = _curLanguage

    //Repository will rule them all
    private val repository : Repository

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
        repository = Repository(getDatabase(application), _curLanguage.value!!.title)
    }


    fun reactRequest(requestString: String)
    {
        coroutineScope.launch {
            processRequest(requestString)
        }
    }

    private suspend fun processRequest(requestString: String)
    {
        val deffered = TraslationNetwork.translate.getTranslation(_curLanguage.value!!.path, requestString)

        try {
            _curResult.value = deffered.await().contents?.translated.toString()
            insertWord(DoneWord(requestString, _curResult.value.toString(), _curLanguage.value!!.title))
        }
        catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            _onError.value = true
        }
    }

    private fun insertWord(word: DoneWord)
    {
        try {
            coroutineScope.launch {
                withContext(Dispatchers.IO){
                    repository.insertWord(word)
                }
            }
        }
        catch (e: Exception){
            Log.e(TAG, e.message)
        }
    }

    fun updateWord(doneWord: DoneWord) {
        coroutineScope.launch {
            withContext(Dispatchers.IO){
                Log.i(TAG, "R $doneWord")
                repository.updateWord(doneWord)
            }
        }
    }

    private fun findWord(word: String)
    {
        var string : String? = ""
        coroutineScope.launch {

            withContext(Dispatchers.IO){
                string = repository.findWord(_curResult.value.toString())
            }
        }
        Log.i("TAG", "INTER $string")
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