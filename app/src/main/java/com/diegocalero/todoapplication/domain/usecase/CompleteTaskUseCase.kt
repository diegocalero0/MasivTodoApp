package com.diegocalero.todoapplication.domain.usecase

import com.diegocalero.todoapplication.data.repository.TasksRepository
import com.diegocalero.todoapplication.domain.model.Task
import javax.inject.Inject

/**
 * Use case that allows mark as complete a task
 */
class CompleteTaskUseCase @Inject constructor(
    private val tasksRepository: TasksRepository
) {
    suspend operator fun invoke(task: Task) {
        task.completed = true
        tasksRepository.updateTask(task)
    }
}