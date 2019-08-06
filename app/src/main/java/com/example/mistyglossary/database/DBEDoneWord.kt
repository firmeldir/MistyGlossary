package com.example.mistyglossary.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mistyglossary.domain.DoneWord

@Entity(tableName = "words")
data class DBEDoneWord (
    var engWord: String,
    var transWord: String,

    var lang: String,
    var saved: Boolean = false
)
{
    @PrimaryKey(autoGenerate = true)
    var wordId : Int = 0
}

fun List<DBEDoneWord>.asDomainModel() : List<DoneWord> = this.map { DoneWord(it.engWord, it.transWord, it.lang, it.saved, it.wordId) }

fun List<DoneWord>.asDBE() : List<DBEDoneWord> = this.map { DBEDoneWord(it.engWord, it.transWord, it.lang, it.saved) }

fun DoneWord.asDBE() : DBEDoneWord = DBEDoneWord(this.engWord, this.transWord, this.lang, this.saved)