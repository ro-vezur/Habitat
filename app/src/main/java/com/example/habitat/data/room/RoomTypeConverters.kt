package com.example.habitat.data.room

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RoomTypeConverters {

    @TypeConverter
    fun fromListStringToString(list: List<String>): String = Json.encodeToString(list)

    @TypeConverter
    fun toListStringFromString(stringList: String): List<String> = Json.decodeFromString(stringList)

}