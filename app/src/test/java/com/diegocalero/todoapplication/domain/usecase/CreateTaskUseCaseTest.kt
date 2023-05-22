package com.diegocalero.todoapplication.domain.usecase

import com.diegocalero.todoapplication.data.repository.TasksRepository
import com.diegocalero.todoapplication.domain.model.Task
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CreateTaskUseCaseTest {
    private val tasksRepositoryMockk = mockk<TasksRepository>()
    private val createTaskUseCase = CreateTaskUseCase(tasksRepositoryMockk)

    @Test
    fun invokeTest() {
        runTest {
            val task = mockk<Task>()
            coEvery { tasksRepositoryMockk.createTask(task) }.returns(Unit)
            createTaskUseCase.invoke(task)
            coVerify { tasksRepositoryMockk.createTask(task) }
        }
    }
}