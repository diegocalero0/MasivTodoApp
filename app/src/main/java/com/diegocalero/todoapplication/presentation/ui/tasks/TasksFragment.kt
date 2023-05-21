package com.diegocalero.todoapplication.presentation.ui.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.diegocalero.todoapplication.databinding.FragmentTasksBinding

/**
 * Fragment where the tasks must be showed and manage by the user
 */
class TasksFragment : Fragment() {

    private lateinit var binding: FragmentTasksBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(layoutInflater)
        initClicks()
        return binding.root
    }

    /**
     * Method that initializes the clicks for
     * view components
     */
    private fun initClicks() {
        binding.buttonCreateTask.setOnClickListener(::navigateToCreateTask)
    }

    /**
     * Method that performs ui navigation to create task screen
     */
    private fun navigateToCreateTask(view: View) {
        findNavController().navigate(TasksFragmentDirections.actionTasksFragmentToCreateTaskFragment())
    }

}