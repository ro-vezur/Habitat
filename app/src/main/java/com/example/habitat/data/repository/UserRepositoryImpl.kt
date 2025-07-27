package com.example.habitat.data.repository

import android.content.Context
import android.util.Log.e
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.habitat.data.entities.UserEntity
import com.example.habitat.domain.entities.User
import com.example.habitat.domain.repository.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context
): UserRepository {
    private companion object {
        val Context.dataStore by preferencesDataStore(name = "user_store")
        val USER_KEY = stringPreferencesKey("user")
        val IS_COMPLETED_REGISTRATION_KEY = booleanPreferencesKey("is_completed_registration")
    }

    override val currentUser: Flow<User> =
        context.dataStore.data.map { preferences ->
            try {
                Json.decodeFromString<UserEntity>(preferences[USER_KEY] ?: "").toDomain()
            } catch (e: Exception) {
                e.printStackTrace()
                User()
            }
        }

    override val isCompletedRegistration: Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            try {
                preferences[IS_COMPLETED_REGISTRATION_KEY] == true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }

    override suspend fun saveUser(userEntity: UserEntity): Flow<Boolean> = flow {
        val json = Json.encodeToString(userEntity)
        context.dataStore.edit { preferences -> preferences[USER_KEY] = json }
        emit(true)
    }.catch { e ->
        e.printStackTrace()
        emit(false)
    }

    override suspend fun saveIsCompletedRegistration(isCompleted: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_COMPLETED_REGISTRATION_KEY] = isCompleted
        }
    }
}