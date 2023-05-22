package com.diegocalero.todoapplication.presentation.ui.createTask

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.diegocalero.todoapplication.MainCoroutineRule
import com.diegocalero.todoapplication.domain.model.Task
import com.diegocalero.todoapplication.domain.usecase.CompleteTaskUseCase
import com.diegocalero.todoapplication.domain.usecase.DeleteTaskUseCase
import com.diegocalero.todoapplication.domain.usecase.GetAllTasksUseCase
import com.diegocalero.todoapplication.presentation.ui.tasks.TasksViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TasksViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val completeTasksUseCaseMockk = mockk<CompleteTaskUseCase>()
    private val deleteTaskUseCaseMockk = mockk<DeleteTaskUseCase>()
    private val getAllTasksUseCaseMockk = mockk<GetAllTasksUseCase>()

    private val tasksViewModel = TasksViewModel(completeTasksUseCaseMockk
        ,deleteTaskUseCaseMockk, getAllTasksUseCaseMockk)

    @Test
    fun initTasksTest() {
        runTest {
            val task1 = mockk<Task>()
            val task2 = mockk<Task>()
            val tasks = listOf<Task>(task1, task2)
            coEvery { getAllTasksUseCaseMockk.invoke() }.returns(tasks)
            tasksViewModel.initTasks()
            advanceUntilIdle()
            coVerify { getAllTasksUseCaseMockk.invoke() }
            Assert.assertEquals(tasks, tasksViewModel.tasks.value)
        }
    }

    @Test
    fun completeTaskUseCase() {
        runTest {
            val task = mockk<Task>()
            val tasks = listOf<Task>(task)
            coEvery { getAllTasksUseCaseMockk.invoke() }.returns(tasks)
            coEvery { completeTasksUseCaseMockk.invoke(task) }.returns(Unit)
            tasksViewModel.completeTask(task)
            advanceUntilIdle()
            coVerify { getAllTasksUseCaseMockk.invoke() }
            coVerify { completeTasksUseCaseMockk.invoke(task) }
            Assert.assertEquals(tasks, tasksViewModel.tasks.value)
            Assert.assertEquals(true, tasksViewModel.taskCompletedResult.value)
        }
    }

    @Test
    fun deleteTaskUseCase() {
        runTest {
            val task = mockk<Task>()
            val tasks = listOf<Task>(task)
            coEvery { getAllTasksUseCaseMockk.invoke() }.returns(tasks)
            coEvery { deleteTaskUseCaseMockk.invoke(task) }.returns(Unit)
            tasksViewModel.deleteTask(task)
            advanceUntilIdle()
            coVerify { getAllTasksUseCaseMockk.invoke() }
            coVerify { deleteTaskUseCaseMockk.invoke(task) }
            Assert.assertEquals(tasks, tasksViewModel.tasks.value)
            Assert.assertEquals(true, tasksViewModel.taskDeletedResult.value)
        }
    }

}