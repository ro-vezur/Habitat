package com.example.habitat.helpers.serializations

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> base64EncodeJsonObjectToString(obj: T): String {
    val json = Json.encodeToString(obj)
    val encoded = java.util.Base64.getUrlEncoder().encodeToString(json.toByteArray())

    return encoded
}