package com.diegocalero.todoapplication.domain.usecase

import com.diegocalero.todoapplication.data.repository.TasksRepository
import com.diegocalero.todoapplication.domain.model.Task
import javax.inject.Inject

/**
 * Use case that creates a Task
 */
class CreateTaskUseCase @Inject constructor(
    private val tasksRepository: TasksRepository
){
    suspend operator fun invoke(task: Task) = tasksRepository.createTask(task)
}