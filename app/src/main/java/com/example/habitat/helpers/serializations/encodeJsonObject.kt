package com.example.habitat.helpers.serializations

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.io.encoding.Base64

inline fun <reified T> encodeJsonObject(obj: T): String {
    val json = Json.encodeToString(obj)
    val encoded = java.util.Base64.getUrlEncoder().encodeToString(json.toByteArray())

    return encoded
}