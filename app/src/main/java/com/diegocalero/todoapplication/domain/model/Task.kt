package com.diegocalero.todoapplication.domain.model

import com.diegocalero.todoapplication.data.local.database.entities.TaskEntity

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val imagePath: String,
    var completed: Boolean = false,
    val createdAt: Long
)

fun TaskEntity.toDomain() = Task(
    id = id,
    title = title,
    description = description,
    imagePath = imagePath,
    createdAt = createdAt,
    completed = completed
)