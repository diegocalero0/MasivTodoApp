package com.diegocalero.todoapplication.domain.usecase

import com.diegocalero.todoapplication.data.repository.TasksRepository
import javax.inject.Inject


/**
 * Use case that returns all tasks
 */
class GetAllTasksUseCase @Inject constructor(
    private val tasksRepository: TasksRepository
){
    suspend operator fun invoke() = tasksRepository.getAllTasks()
}