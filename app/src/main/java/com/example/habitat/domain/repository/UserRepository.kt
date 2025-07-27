package com.example.habitat.domain.repository

import com.example.habitat.data.entities.UserEntity
import com.example.habitat.domain.entities.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val currentUser: Flow<User>
    val isCompletedRegistration: Flow<Boolean>

    suspend fun saveUser(userEntity: UserEntity): Flow<Boolean>
    suspend fun saveIsCompletedRegistration(isCompleted: Boolean)
}