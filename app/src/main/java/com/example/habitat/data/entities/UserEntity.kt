package com.example.habitat.data.entities

import com.example.habitat.domain.entities.User
import com.example.habitat.enums.HabitsCategories
import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(
    val name: String = "",
    val age: Int = 0,
    val livingPlace: String = "",
    val preferredHabits: List<String> = emptyList(),
) {
    fun toDomain(): User {
        return User(
            name = name,
            age = age,
            livingPlace = livingPlace,
            preferredHabits = preferredHabits.map { HabitsCategories.valueOf(it) }
        )
    }
}