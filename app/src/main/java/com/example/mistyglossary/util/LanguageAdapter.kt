package com.example.mistyglossary.util

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mistyglossary.databinding.LanguageItemBinding
import com.example.mistyglossary.domain.MistyLanguage


class LanguageAdapter : RecyclerView.Adapter<LanguageAdapter.LanguageView>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageView {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: LanguageView, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    class LanguageView(binding: LanguageItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        lateinit var languageData : MistyLanguage

        fun bind(language: MistyLanguage)
        {
        }
    }
}


