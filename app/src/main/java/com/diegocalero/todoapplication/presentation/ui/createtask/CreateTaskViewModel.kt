package com.diegocalero.todoapplication.presentation.ui.createtask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel That contains the info and logic to show on create task screen
 */
class CreateTaskViewModel: ViewModel() {

    val imagePath: MutableLiveData<String?> = MutableLiveData<String?>()
    val taskTitle: MutableLiveData<String> = MutableLiveData<String>("")
    val taskDescription: MutableLiveData<String> = MutableLiveData<String>("")

    /**
     * Function that creates the task with the current
     * [imagePath], [taskTitle], and [taskDescription] values
     */
    fun createTask() {

    }

}