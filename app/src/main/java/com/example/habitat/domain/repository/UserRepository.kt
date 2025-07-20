package com.example.habitat.domain.repository

import com.example.habitat.data.entities.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val currentUser: Flow<UserEntity>
    val isCompletedRegistration: Flow<Boolean>

    suspend fun saveUser(userEntity: UserEntity): Flow<Boolean>
    suspend fun saveIsCompletedRegistration(isCompleted: Boolean)
}