package com.diegocalero.todoapplication.data.repository

import com.diegocalero.todoapplication.data.local.database.daos.TaskDao
import com.diegocalero.todoapplication.data.local.database.entities.TaskEntity
import com.diegocalero.todoapplication.data.local.database.entities.toEntity
import com.diegocalero.todoapplication.domain.model.Task
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class TasksRepositoryTest {

    private val taskDaoMockk = mockk<TaskDao>()
    private val tasksRepository = TasksRepository(taskDaoMockk)

    @Test
    fun getAllTasksTask() {
        runTest {
            val task1 = mockk<TaskEntity>()
            val tasks = listOf<TaskEntity>(task1)
            every { task1.id }.returns(1)
            every { task1.createdAt }.returns(1)
            every { task1.title }.returns("Title")
            every { task1.description }.returns("Description")
            every { task1.imagePath }.returns("Path")
            every { task1.completed }.returns(false)
            coEvery {
                taskDaoMockk.getAllTasks()
            }.returns(tasks)
            val tasksResult = tasksRepository.getAllTasks()
            Assert.assertEquals(tasksResult.size, tasks.size)
            verify { task1.id }
            verify { task1.createdAt }
            verify { task1.title }
            verify { task1.description }
            verify { task1.imagePath }
            verify { task1.completed }
            coVerify {
                taskDaoMockk.getAllTasks()
            }
        }
    }

    @Test
    fun createTaskTest() {
        runTest {
            val task = mockk<Task>()
            val taskEntity = mockk<TaskEntity>()
            mockkStatic(task::toEntity)
            every { task.toEntity() }.returns(taskEntity)
            coEvery { taskDaoMockk.insertTask(taskEntity) }.returns(Unit)
            tasksRepository.createTask(task)
            coVerify { taskDaoMockk.insertTask(taskEntity)  }
        }
    }

    @Test
    fun updateTaskTest() {
        runTest {
            val task = mockk<Task>()
            val taskEntity = mockk<TaskEntity>()
            mockkStatic(task::toEntity)
            every { task.toEntity() }.returns(taskEntity)
            coEvery { taskDaoMockk.updateTask(taskEntity) }.returns(Unit)
            tasksRepository.updateTask(task)
            coVerify { taskDaoMockk.updateTask(taskEntity)  }
        }
    }

    @Test
    fun deleteTaskTest() {
        runTest {
            val task = mockk<Task>()
            val taskEntity = mockk<TaskEntity>()
            mockkStatic(task::toEntity)
            every { task.toEntity() }.returns(taskEntity)
            coEvery { taskDaoMockk.deleteTask(taskEntity) }.returns(Unit)
            tasksRepository.deleteTask(task)
            coVerify { taskDaoMockk.deleteTask(taskEntity)  }
        }
    }

}