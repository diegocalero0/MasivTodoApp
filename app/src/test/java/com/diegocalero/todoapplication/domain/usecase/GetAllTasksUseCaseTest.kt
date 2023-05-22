package com.diegocalero.todoapplication.domain.usecase

import com.diegocalero.todoapplication.data.local.database.entities.TaskEntity
import com.diegocalero.todoapplication.data.repository.TasksRepository
import com.diegocalero.todoapplication.domain.model.Task
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class GetAllTasksUseCaseTest {

    private val tasksRepositoryMockk = mockk<TasksRepository>()
    private val getAllTaskUseCase = GetAllTasksUseCase(tasksRepositoryMockk)

    @Test
    fun invokeTest() {
        runTest {
            val task = mockk<Task>()
            val tasks = listOf(task)
            coEvery { tasksRepositoryMockk.getAllTasks() }.returns(tasks)
            val responseTask: List<Task> = getAllTaskUseCase.invoke()
            coVerify { tasksRepositoryMockk.getAllTasks() }
            Assert.assertEquals(tasks, responseTask)
        }
    }
}