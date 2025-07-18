package com.example.habitat.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.habitat.data.entities.HabitEntity

@TypeConverters(RoomTypeConverters::class)
@Database(entities = [HabitEntity::class], version = 2)
abstract class HabitsLocalDataBase: RoomDatabase() {
    abstract fun habitsDao(): HabitsDao
}