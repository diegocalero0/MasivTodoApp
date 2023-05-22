package com.diegocalero.todoapplication.domain.usecase

import com.diegocalero.todoapplication.data.repository.TasksRepository
import com.diegocalero.todoapplication.domain.model.Task
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CompleteTaskUseCaseTest {

    private val tasksRepositoryMockk = mockk<TasksRepository>()
    private val completeTaskUseCase = CompleteTaskUseCase(tasksRepositoryMockk)

    @Test
    fun invokeTest() {
        runTest {
            val task = mockk<Task>()
            every { task.completed = true }.returns(Unit)
            coEvery { tasksRepositoryMockk.updateTask(task) }.returns(Unit)
            completeTaskUseCase.invoke(task)
            verify { task.completed = true }
            coVerify { tasksRepositoryMockk.updateTask(task) }
        }
    }
}