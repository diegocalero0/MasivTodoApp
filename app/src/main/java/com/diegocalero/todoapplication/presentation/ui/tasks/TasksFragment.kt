package com.diegocalero.todoapplication.presentation.ui.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diegocalero.todoapplication.R
import com.diegocalero.todoapplication.databinding.FragmentTasksBinding
import com.diegocalero.todoapplication.domain.model.Task
import com.diegocalero.todoapplication.presentation.ui.tasks.adapters.TaskAdapter
import com.diegocalero.todoapplication.presentation.ui.tasks.adapters.TaskViewHolder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment where the tasks must be showed and manage by the user
 */
@AndroidEntryPoint
class TasksFragment : Fragment(), TaskViewHolder.TaskClicksListener {

    private lateinit var binding: FragmentTasksBinding
    private val viewModel: TasksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(layoutInflater)
        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        initClicks()
        initObservers()
        viewModel.initTasks()
        return binding.root
    }

    /**
     * Function that initializes the clicks for
     * view components
     */
    private fun initClicks() {
        binding.buttonCreateTask.setOnClickListener { navigateToCreateTask() }
        binding.floatingActionButtonCreateTask.setOnClickListener { navigateToCreateTask() }
    }

    /**
     * Function that initialize the mutable data from viewModel
     */
    private fun initObservers() {
        viewModel.tasks.observe(viewLifecycleOwner) {
            if(it.isNullOrEmpty()) {
                binding.recyclerViewTasks.visibility = View.GONE
                binding.floatingActionButtonCreateTask.visibility = View.GONE
                binding.constraintLayoutEmptyState.visibility = View.VISIBLE
            } else {
                binding.recyclerViewTasks.visibility = View.VISIBLE
                binding.floatingActionButtonCreateTask.visibility = View.VISIBLE
                binding.constraintLayoutEmptyState.visibility = View.GONE
                binding.recyclerViewTasks.adapter = TaskAdapter(it, this)
            }
        }
        viewModel.taskCompletedResult.observe(viewLifecycleOwner) {
            it?.let {
                if(it) {
                    showSnackBar(resources.getString(R.string.task_completed))
                } else {
                    showSnackBar(resources.getString(R.string.error_updating_task))
                }
                viewModel.taskCompletedResult.postValue(null)
            }
        }
        viewModel.taskDeletedResult.observe(viewLifecycleOwner) {
            it?.let {
                if(it) {
                    showSnackBar(resources.getString(R.string.task_deleted))
                } else {
                    showSnackBar(resources.getString(R.string.error_updating_task))
                }
                viewModel.taskDeletedResult.postValue(null)
            }
        }
    }

    /**
     * Function that performs ui navigation to create task screen
     */
    private fun navigateToCreateTask() {
        findNavController().navigate(TasksFragmentDirections.actionTasksFragmentToCreateTaskFragment())
    }

    private fun showSnackBar(text: String) {
        val snackBar = Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG)
        snackBar.show()
    }

    override fun onClickCompleteTask(task: Task) {
        viewModel.completeTask(task)
    }

    override fun onClickRemoveTask(task: Task) {
        viewModel.deleteTask(task)
    }

}