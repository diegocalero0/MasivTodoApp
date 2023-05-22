package com.diegocalero.todoapplication.domain.usecase

import com.diegocalero.todoapplication.data.repository.TasksRepository
import com.diegocalero.todoapplication.domain.model.Task
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DeleteTaskUseCaseTest {

    private val tasksRepositoryMockk = mockk<TasksRepository>()
    private val deleteTaskUseCase = DeleteTaskUseCase(tasksRepositoryMockk)

    @Test
    fun invokeTest() {
        runTest {
            val task = mockk<Task>()
            coEvery { tasksRepositoryMockk.deleteTask(task) }.returns(Unit)
            deleteTaskUseCase.invoke(task)
            coVerify { tasksRepositoryMockk.deleteTask(task) }
        }
    }
}