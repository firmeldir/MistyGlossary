package com.example.mistyglossary.util

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mistyglossary.databinding.WordItemBinding
import com.example.mistyglossary.domain.DoneWord
import android.R.attr.start
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.util.Log
import com.example.mistyglossary.viewmodels.TAG


class WordAdapter(val listener: WordListener, private val savedDrawable: Drawable,
                  private val unsavedDrawable: Drawable) : ListAdapter<DoneWord, WordAdapter.WordViewHolder>(WordDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder = WordViewHolder.from(parent, listener)

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) = holder.bind(getItem(position), savedDrawable, unsavedDrawable)

    class WordViewHolder(val binding: WordItemBinding, val listener: WordListener) : RecyclerView.ViewHolder(binding.root) {

        private val animatorSet = AnimatorSet()

        fun bind(item: DoneWord, savedDrawable: Drawable?,unsavedDrawable: Drawable?){
            binding.word = item

            if (item.saved){
                binding.imageView3.setImageDrawable(savedDrawable)
                Log.i(TAG, "START true")
            }
            else{
                binding.imageView3.setImageDrawable(unsavedDrawable)
                Log.i(TAG, "START false")
            }

            binding.listener = WordListener {
                val toChange = it
                toChange.saved = !toChange.saved
                listener.clickListener(toChange)

                animatorSet.removeAllListeners()
                animatorSet.end()
                animatorSet.cancel()

                val anim = ObjectAnimator.ofFloat(binding.imageView3, "rotation", 360f)
                anim.duration = 300

                animatorSet.play(anim)
                animatorSet.start()

                if (toChange.saved){
                    binding.imageView3.setImageDrawable(savedDrawable)
                }
                else{
                    binding.imageView3.setImageDrawable(unsavedDrawable)
                }
            }

            binding.executePendingBindings()
        }


        companion object{
            fun from(parent: ViewGroup, listener: WordListener) : WordViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WordItemBinding.inflate(layoutInflater, parent, false)
                return WordViewHolder(binding, listener)
            }
        }
    }


    class WordDiffCallback : DiffUtil.ItemCallback<DoneWord>() {
        override fun areItemsTheSame(oldItem: DoneWord, newItem: DoneWord): Boolean = oldItem.wordId == newItem.wordId
        override fun areContentsTheSame(oldItem: DoneWord, newItem: DoneWord): Boolean = oldItem == newItem
    }

    class WordListener(val clickListener: (doneWord: DoneWord) -> Unit) {
        fun onClick(word: DoneWord) = clickListener(word)
    }
}