package com.example.mistyglossary.util

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mistyglossary.databinding.LanguageItemBinding
import com.example.mistyglossary.domain.MistyLanguage


class LanguageAdapter(val lanListener: LanguageListener) : ListAdapter<MistyLanguage, LanguageAdapter.LanguageView>(MistyLanDiffCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageView = LanguageView.from(parent)

    override fun onBindViewHolder(holder: LanguageView, position: Int) = holder.bind(getItem(position), lanListener)


    class LanguageView(val binding: LanguageItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        lateinit var languageData : MistyLanguage

        fun bind(item: MistyLanguage,lanListener: LanguageListener) {
            languageData = item
            Log.i("TAG", item.toString())
            binding.language = item
            binding.listener = lanListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): LanguageView {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LanguageItemBinding.inflate(layoutInflater, parent, false)
                return LanguageView(binding)
            }
        }
    }

    class MistyLanDiffCallback : DiffUtil.ItemCallback<MistyLanguage>() {

        override fun areItemsTheSame(oldItem: MistyLanguage, newItem: MistyLanguage): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: MistyLanguage, newItem: MistyLanguage): Boolean {
            return oldItem.title == newItem.title
        }
    }

    class LanguageListener(val clickListener: (id: Int) -> Unit) {
        fun onClick(mistyLanguage: MistyLanguage) = clickListener(mistyLanguage.id)
    }
}


