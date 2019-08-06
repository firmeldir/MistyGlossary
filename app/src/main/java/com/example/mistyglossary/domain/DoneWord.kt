package com.example.mistyglossary.domain


data class DoneWord (
    val engWord: String,
    val transWord: String,

    val lang: String,
    val saved: Boolean = false,
    val wordId : Int = 0
)
