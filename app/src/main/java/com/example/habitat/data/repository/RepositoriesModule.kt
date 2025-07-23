package com.example.habitat.data.repository

import android.content.Context
import com.example.habitat.data.room.HabitsDao
import com.example.habitat.domain.repository.HabitsRepository
import com.example.habitat.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    @Singleton
    fun providesHabitRepository(
        @ApplicationContext context: Context,
        habitsDao: HabitsDao
    ): HabitsRepository {
        return HabitsRepositoryImpl(context = context, habitsDao = habitsDao)
    }

    @Provides
    @Singleton
    fun providesUserRepository(
        @ApplicationContext context: Context
    ): UserRepository {
        return UserRepositoryImpl(context = context)
    }

}