package com.diegocalero.todoapplication.presentation.ui.createtask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diegocalero.todoapplication.domain.model.Task
import com.diegocalero.todoapplication.domain.usecase.CreateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel That contains the info and logic to show on create task screen
 */
@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val createTaskUseCase: CreateTaskUseCase
): ViewModel() {

    val createTaskResult: MutableLiveData<Boolean?> = MutableLiveData<Boolean?>()
    val imagePath: MutableLiveData<String?> = MutableLiveData<String?>()
    val taskTitle: MutableLiveData<String> = MutableLiveData<String>("")
    val taskDescription: MutableLiveData<String> = MutableLiveData<String>("")

    /**
     * Function that creates the task with the current
     * [imagePath], [taskTitle], and [taskDescription] values
     */
    fun createTask() {
        viewModelScope.launch {
            val task = Task(
                id = 0,
                title = taskTitle.value.toString(),
                imagePath = imagePath.value.toString(),
                description = taskDescription.value.toString(),
                createdAt = System.currentTimeMillis()
            )
            createTaskUseCase.invoke(task)
            createTaskResult.postValue(true)
        }
    }
}