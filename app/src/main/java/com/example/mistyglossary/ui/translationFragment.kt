package com.example.mistyglossary.ui


import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide

import com.example.mistyglossary.R
import com.example.mistyglossary.databinding.FragmentTranslationBinding
import com.example.mistyglossary.viewmodels.TranslationViewModel
import com.google.android.material.snackbar.Snackbar

class TranslationFragment : Fragment() {

    private var lanId : Int = 0

    lateinit var binding: FragmentTranslationBinding

    private val translationViewModel : TranslationViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProviders.of(this, TranslationViewModel.TranslationViewModelFactory(lanId,activity.application)).get(TranslationViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_translation, container, false)

        binding.lifecycleOwner = this

        lanId = TranslationFragmentArgs.fromBundle(arguments!!).lanId

        binding.viewModel = translationViewModel
        binding.included.viewModel = translationViewModel

        binding.included.translateButton.setOnClickListener {
           translationViewModel.reactRequest(binding.included.textInput.text.toString())
        }

        translationViewModel.onError.observe(this, Observer {
            if (it == true) {
                Snackbar.make(binding.root, resources.getString(R.string.error), Snackbar.LENGTH_SHORT).show()
                translationViewModel.onErrorReact()
            }
        })

        //postponeEnterTransition()
//        (view?.parent as? ViewGroup)?.doOnPreDraw {
//            // Parent has been drawn. Start transitioning!
//            Log.i("TAG", "please+(((")
        //startPostponedEnterTransition()
        //}

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }
}
