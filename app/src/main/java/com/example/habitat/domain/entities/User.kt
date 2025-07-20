package com.example.habitat.domain.entities

import com.example.habitat.data.entities.UserEntity
import com.example.habitat.enums.HabitsCategories

data class User(
    val name: String,
    val age: Int = 0,
    val livingPlace: String,
    val preferredHabits: List<HabitsCategories> = emptyList()
) {
    fun toEntity(): UserEntity {
        return UserEntity(
            name = name,
            age = age,
            livingPlace = livingPlace,
            preferredHabits = preferredHabits.map { it.name }
        )
    }
}
