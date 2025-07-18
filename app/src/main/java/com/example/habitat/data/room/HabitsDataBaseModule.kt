package com.example.habitat.data.room

import android.R.attr.name
import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.habitat.HABITS_LOCAL_DATA_BASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HabitsDataBaseModule {

    @Provides
    fun providesHabitsDao(db: HabitsLocalDataBase): HabitsDao = db.habitsDao()

    @Provides
    fun provideHabitsLocalDataBase(
        @ApplicationContext context: Context
    ): HabitsLocalDataBase {
        val MIGRATION_1_2 = object : Migration(1,2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("DROP TABLE IF EXISTS habits_v1;")
                db.execSQL("ALTER TABLE habits RENAME TO habits_v1 ")
                db.execSQL("CREATE TABLE IF NOT EXISTS `habits_v2` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `description` TEXT NOT NULL, `category` TEXT NULL, `remindTime` INTEGER NOT NULL, `periodicity` TEXT NOT NULL, `repeatEveryWeek` INTEGER NOT NULL, 'timeOfCreation' INTEGER NOT NULL, 'completedDates' TEXT NOT NULL)")
                db.execSQL("INSERT INTO habits_v2 SELECT * FROM habits_v1")
                db.execSQL("DROP TABLE IF EXISTS habits_v1")
            }
        }

        return Room.databaseBuilder(
            context = context.applicationContext,
            HabitsLocalDataBase::class.java,
            name = HABITS_LOCAL_DATA_BASE_NAME
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }
}