package com.example.habitat.data.room

import android.content.Context
import androidx.room.Room
import com.example.habitat.HABITS_LOCAL_DATA_BASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun providesHabitsDao(db: HabitsLocalDataBase): HabitsDao = db.habitsDao()

    @Provides
    @Singleton
    fun provideHabitsLocalDataBase(
        @ApplicationContext context: Context
    ): HabitsLocalDataBase {
        return Room.databaseBuilder(
            context = context.applicationContext,
            HabitsLocalDataBase::class.java,
            name = HABITS_LOCAL_DATA_BASE_NAME
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }
}