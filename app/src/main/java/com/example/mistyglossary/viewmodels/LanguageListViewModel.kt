package com.example.mistyglossary.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.bumptech.glide.Glide.init
import com.example.mistyglossary.domain.MistyLanguage
import com.example.mistyglossary.util.initializeLanguages


class LanguageListViewModel(application: Application) : AndroidViewModel(application)
{

    private val _mistyLanguage : MutableLiveData<List<MistyLanguage>> by lazy{
        MutableLiveData<List<MistyLanguage>>()
    }

    val mistyLanguage : LiveData<List<MistyLanguage>>
        get() = _mistyLanguage

    private val _navigateToLanguagetrans = MutableLiveData<Int>()
    val navigateToLanguagetrans : LiveData<Int>
        get() = _navigateToLanguagetrans

    fun onLanguageClicked(id: Int) {
        _navigateToLanguagetrans.value = id
    }

    fun onLanguageNavigated() {
        _navigateToLanguagetrans.value = null
    }


    init {
        _mistyLanguage.value = initializeLanguages(application as Context)
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LanguageListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LanguageListViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}

