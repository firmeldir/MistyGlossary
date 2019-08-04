package com.example.mistyglossary.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.mistyglossary.R
import com.example.mistyglossary.databinding.FragmentLanguageBinding
import com.example.mistyglossary.databinding.LanguageItemBinding
import com.example.mistyglossary.util.LanguageAdapter
import com.example.mistyglossary.viewmodels.LanguageListViewModel
import kotlinx.android.synthetic.main.language_item.*

class LanguageFragment : Fragment() {

    private lateinit var binding: FragmentLanguageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_language, container, false)

        val application = requireNotNull(this.activity).application
        val factory = LanguageListViewModel.Factory(application)

        val viewModel = ViewModelProviders.of(this, factory).get(LanguageListViewModel::class.java)

        val adapter = LanguageAdapter(LanguageAdapter.LanguageListener {
            viewModel.onLanguageClicked(it)
        })


        binding.languageList.adapter = adapter
        binding.languageList.layoutManager = LinearLayoutManager(this.context)

        viewModel.mistyLanguage.observe(viewLifecycleOwner,Observer {
            it?.let{
                adapter.submitList(it)
            }
        })

        viewModel.navigateToLanguagetrans.observe(this, Observer {

            it?.let {

                val extras = FragmentNavigatorExtras(
                    imageView to "header_image"
                )

                val action = LanguageFragmentDirections.toTransFrag() ; action.lanId = it
                findNavController().navigate(action,extras)

                viewModel.onLanguageNavigated()
            }
        })

        binding.lifecycleOwner = this

        return binding.root
    }


}
