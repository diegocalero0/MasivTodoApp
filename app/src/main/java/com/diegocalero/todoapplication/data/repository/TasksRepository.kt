package com.diegocalero.todoapplication.data.repository

import com.diegocalero.todoapplication.data.local.database.daos.TaskDao
import com.diegocalero.todoapplication.data.local.database.entities.toEntity
import com.diegocalero.todoapplication.domain.model.Task
import com.diegocalero.todoapplication.domain.model.toDomain
import javax.inject.Inject

/**
 * Repository that provides data about tasks
 */
class TasksRepository @Inject constructor(
    private val taskDao: TaskDao
) {

    /**
     * Function that returns all created tasks
     */
    suspend fun getAllTasks(): List<Task> {
        return taskDao.getAllTasks().map {
            it.toDomain()
        }
    }

    /**
     * Function that inserts a tasks in to database
     */
    suspend fun createTask(task: Task) {
        taskDao.insertTask(task = task.toEntity())
    }

    /**
     * Function that updates a task
     */
    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toEntity())
    }

    /**
     * Function that deletes a task
     */
    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task.toEntity())
    }
}