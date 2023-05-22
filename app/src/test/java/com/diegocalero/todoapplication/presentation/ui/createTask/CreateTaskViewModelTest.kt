package com.diegocalero.todoapplication.presentation.ui.createTask

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.diegocalero.todoapplication.MainCoroutineRule
import com.diegocalero.todoapplication.domain.usecase.CreateTaskUseCase
import com.diegocalero.todoapplication.presentation.ui.createtask.CreateTaskViewModel
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
class CreateTaskViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val createTaskUseCaseMockk = mockk<CreateTaskUseCase>()
    private val createTaskViewModel = CreateTaskViewModel(createTaskUseCaseMockk)

    @Test
    fun createTaskTest() {
        runTest {
            coEvery { createTaskUseCaseMockk.invoke(any()) }.returns(Unit)
            createTaskViewModel.createTask()
            advanceUntilIdle()
            coVerify { createTaskUseCaseMockk.invoke(any()) }
            Assert.assertEquals(true, createTaskViewModel.createTaskResult.value)
        }
    }

}