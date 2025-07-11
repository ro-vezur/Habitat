package com.example.habitat.helpers.serializations

import kotlinx.serialization.json.Json
import java.util.Base64

inline fun <reified T> decodeJsonObject(json: String): T {
    val json = String(Base64.getUrlDecoder().decode(json))
    val obj = Json.decodeFromString<T>(json)

    return obj
}