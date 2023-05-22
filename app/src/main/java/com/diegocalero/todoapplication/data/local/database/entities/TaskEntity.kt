package com.diegocalero.todoapplication.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.diegocalero.todoapplication.domain.model.Task

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "imagePath") val imagePath: String,
    @ColumnInfo(name = "completed") val completed: Boolean,
    @ColumnInfo(name = "createdAt") val createdAt: Long
)

fun Task.toEntity() = TaskEntity(
    id = id,
    title = title,
    description = description,
    imagePath = imagePath,
    createdAt = createdAt,
    completed = completed
)

