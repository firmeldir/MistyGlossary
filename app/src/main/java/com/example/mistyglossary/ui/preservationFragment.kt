package com.example.mistyglossary.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.mistyglossary.R
import com.example.mistyglossary.databinding.FragmentPreservationBinding
import com.example.mistyglossary.util.WordAdapter
import com.example.mistyglossary.viewmodels.PreservationViewModel
import com.example.mistyglossary.viewmodels.TranslationViewModel

class PreservationFragment : Fragment() {

    lateinit var binding: FragmentPreservationBinding

    private val preservationViewModel : PreservationViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProviders.of(this, PreservationViewModel.PreservationViewModelFactory(activity.application)).get(
            PreservationViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentPreservationBinding.inflate(layoutInflater, container, false)

        val adapter = WordAdapter()
        binding.savedList.adapter = adapter
        preservationViewModel.savedWords.observe(this, Observer {
            adapter.submitList(it)
        })


        return binding.root
    }


}
