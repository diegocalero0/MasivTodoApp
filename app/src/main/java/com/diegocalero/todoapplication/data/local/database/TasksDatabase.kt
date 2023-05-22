package com.diegocalero.todoapplication.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.diegocalero.todoapplication.data.local.database.daos.TaskDao
import com.diegocalero.todoapplication.data.local.database.entities.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1
)
abstract class TasksDatabase: RoomDatabase() {
    abstract fun getTaskDao(): TaskDao
}