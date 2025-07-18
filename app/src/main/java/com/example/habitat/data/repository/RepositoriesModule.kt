package com.example.habitat.data.repository

import android.content.Context
import com.example.habitat.data.room.HabitsDao
import com.example.habitat.domain.repository.HabitsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    fun providesHabitRepository(
        @ApplicationContext context: Context,
        habitsDao: HabitsDao
    ): HabitsRepository {
        return HabitsRepositoryImpl(context = context, habitsDao = habitsDao)
    }

}