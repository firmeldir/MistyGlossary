package com.example.mistyglossary.viewmodels

import android.app.Application
import android.os.Build.VERSION_CODES.M
import android.util.Log
import androidx.lifecycle.*
import com.example.mistyglossary.domain.MistyLanguage
import com.example.mistyglossary.network.TraslationNetwork
import com.example.mistyglossary.network.translateResponse
import com.example.mistyglossary.util.initializeLanguage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import javax.security.auth.callback.Callback

class TranslationViewModel(val curLanId: Int, application: Application) : AndroidViewModel(application)
{
    private val _curLanguage = MutableLiveData<MistyLanguage>()

    val curLanguage : LiveData<MistyLanguage>
        get() = _curLanguage


    private val _curResult = MutableLiveData<String>()

    val curResult : LiveData<String>
        get() = _curResult


    private val _onError : MutableLiveData<Boolean> = MutableLiveData()
    val onError : LiveData<Boolean>
        get() = _onError

    private val coroutineJob = Job()

    private val coroutineScope = CoroutineScope(coroutineJob + Dispatchers.Main)

    init {
        _curLanguage.value = initializeLanguage(curLanId,getApplication())
        _onError.value = false
    }


    fun reactRequest(requestString: String)
    {
        coroutineScope.launch {
            processRequest(requestString)
        }
    }

    fun onErrorReact(){_onError.value = false}

    suspend fun processRequest(requestString: String)
    {
        val deffered = TraslationNetwork.translate.getTranslation(_curLanguage.value!!.path, requestString)

        try {
            _curResult.value = deffered.await().contents?.translated.toString()
        }
        catch (e: Exception) {
            Log.e("TAG", e.message.toString())
            _onError.value = true
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