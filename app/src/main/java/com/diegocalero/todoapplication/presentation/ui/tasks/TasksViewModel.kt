package com.diegocalero.todoapplication.presentation.ui.tasks

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegocalero.todoapplication.domain.model.Task
import com.diegocalero.todoapplication.domain.usecase.CompleteTaskUseCase
import com.diegocalero.todoapplication.domain.usecase.DeleteTaskUseCase
import com.diegocalero.todoapplication.domain.usecase.GetAllTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val completeTaskUseCase: CompleteTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getAllTasksUseCase: GetAllTasksUseCase
): ViewModel() {

    val taskCompletedResult: MutableLiveData<Boolean?> = MutableLiveData<Boolean?>()
    val taskDeletedResult: MutableLiveData<Boolean?> = MutableLiveData<Boolean?>()
    val tasks: MutableLiveData<List<Task>> = MutableLiveData<List<Task>>()

    /**
     * Function that gets all tasks and save it to [tasks]
     */
    fun initTasks() {
        viewModelScope.launch {
            tasks.postValue(getAllTasksUseCase.invoke())
        }
    }

    /**
     * Function that mark as completed a task
     */
    fun completeTask(task: Task) {
        viewModelScope.launch {
            completeTaskUseCase.invoke(task)
            taskCompletedResult.postValue(true)
            initTasks()
        }
    }

    /**
     * Function that deletes a task
     */
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            deleteTaskUseCase.invoke(task)
            taskDeletedResult.postValue(true)
            initTasks()
        }
    }

}