package com.example.mistyglossary.util

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mistyglossary.databinding.WordItemBinding
import com.example.mistyglossary.domain.DoneWord
import com.example.mistyglossary.viewmodels.TAG

class WordAdapter : ListAdapter<DoneWord, WordAdapter.WordViewHolder>(WordDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder = WordViewHolder.from(parent)

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) = holder.bind(getItem(position))

    class WordViewHolder(val binding: WordItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DoneWord){
            binding.word = item
            Log.i(TAG, "BINDING + ${item.toString()}")
            binding.executePendingBindings()
        }


        companion object{
            fun from(parent: ViewGroup) : WordViewHolder{
                Log.i(TAG, "CREATION")
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WordItemBinding.inflate(layoutInflater, parent, false)
                return WordViewHolder(binding)
            }
        }
    }


    class WordDiffCallback : DiffUtil.ItemCallback<DoneWord>() {
        override fun areItemsTheSame(oldItem: DoneWord, newItem: DoneWord): Boolean {return oldItem.wordId == newItem.wordId}

        override fun areContentsTheSame(oldItem: DoneWord, newItem: DoneWord): Boolean {return oldItem == newItem}
    }
}