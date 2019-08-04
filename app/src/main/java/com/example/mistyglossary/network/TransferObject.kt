package com.example.mistyglossary.network

import com.squareup.moshi.Json

data class Contents (
    @Json(name = "translation")
    var translation: String? = null,
    @Json(name = "text")
    var text: String? = null,
    @Json(name = "translated")
    var translated: String? = null
)

data class Success(
    @Json(name = "total")
    var total: Int? = null)

data class translateResponse (
    @Json(name = "success")
    var success: Success? = null,
    @Json(name = "contents")
    var contents: Contents? = null
)