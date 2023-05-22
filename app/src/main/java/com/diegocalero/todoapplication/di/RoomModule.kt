package com.diegocalero.todoapplication.di

import android.content.Context
import androidx.room.Room
import com.diegocalero.todoapplication.data.local.database.TasksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val TASKS_DATABASE_NAME = "tasks_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context)
        = Room.databaseBuilder(context, TasksDatabase::class.java, TASKS_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideTaskDao(tasksDatabase: TasksDatabase) = tasksDatabase.getTaskDao()
}