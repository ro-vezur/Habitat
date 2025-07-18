package com.example.habitat.data.room

import androidx.room.TypeConverter
import com.example.habitat.helpers.serializations.base64DecodeJsonObject
import com.example.habitat.helpers.serializations.base64EncodeJsonObjectToString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RoomTypeConverters {

    @TypeConverter
    fun fromListStringToString(list: List<String>): String = Json.encodeToString(list)

    @TypeConverter
    fun toListStringFromString(json: String): List<String> = Json.decodeFromString(json)

    @TypeConverter
    fun fromListLongToString(list: List<Long>): String = Json.encodeToString(list)

    @TypeConverter
    fun toListLongFromString(json: String): List<Long> = Json.decodeFromString(json)
}