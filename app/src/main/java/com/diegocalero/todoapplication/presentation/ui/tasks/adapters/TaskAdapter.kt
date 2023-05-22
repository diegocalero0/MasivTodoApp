package com.diegocalero.todoapplication.presentation.ui.tasks.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.diegocalero.todoapplication.R
import com.diegocalero.todoapplication.databinding.ItemListTaskBinding
import com.diegocalero.todoapplication.domain.model.Task
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Adapter for recycler view that contains and represents the tasks
 */
class TaskAdapter(private val items: List<Task>, private val listener: TaskViewHolder.TaskClicksListener): RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class TaskViewHolder(private val binding: ItemListTaskBinding, private val listener: TaskClicksListener): ViewHolder(binding.root) {
    fun bind(task: Task) {
        if(task.completed) {
            binding.textViewCompleted.visibility = View.VISIBLE
            binding.imageButtonCompleteTask.visibility = View.GONE
        } else {
            binding.textViewCompleted.visibility = View.GONE
            binding.imageButtonCompleteTask.visibility = View.VISIBLE
        }
        binding.textViewTitle.text = task.title
        binding.textViewTaskDescription.text = task.description
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedDate = format.format(Date(task.createdAt))
        binding.textViewCreationDate.text = formattedDate
        val imageFile = File(task.imagePath)

        Glide.with(itemView).load(Uri.fromFile(imageFile)).placeholder(ResourcesCompat.getDrawable(
            itemView.resources, R.drawable.placeholder, null)).into(binding.imageViewTask);


        binding.imageButtonCompleteTask.setOnClickListener {
            listener.onClickCompleteTask(task)
        }
        binding.imageButtonDeleteTask.setOnClickListener {
            listener.onClickRemoveTask(task)
        }
    }

    interface TaskClicksListener {
        /**
         * Function called when user perform click on complete task
         */
        fun onClickCompleteTask(task: Task)

        /**
         * Function called when user perform click on remove task
         */
        fun onClickRemoveTask(task: Task)
    }

}