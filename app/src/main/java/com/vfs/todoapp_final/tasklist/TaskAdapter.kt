package com.vfs.todoapp_final.tasklist

import com.vfs.todoapp_final.models.Category
import com.vfs.todoapp_final.models.Task

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.vfs.todoapp_final.R

/**
 * Adapter for to-do and finished task lists
 */
class TaskAdapter(category : Category, val listener : TaskClickedListener, private val bTodoList :  Boolean) : RecyclerView.Adapter<TaskViewHolder>() {

    // If displaying the to-do list of finished task list
    private var taskList : MutableList<Task> = if (bTodoList)
        category.todoTaskList
    else
        category.finishedTaskList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val taskRoot = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.task_row, parent, false)

        // Different viewHolder for to-do task and finished task list
        return if (bTodoList)
            TodoTaskViewHolder(taskRoot as LinearLayout)
        else
            FinishedTaskViewHolder(taskRoot as LinearLayout)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]

        holder.taskContainer?.setOnClickListener {
            listener.onShortClick(holder.adapterPosition)
        }

        holder.taskContainer?.setOnLongClickListener {
            listener.onLongClick(holder.adapterPosition)
            true
        }

        holder.bindCategory(task, position == taskList.count() - 1)
    }

    override fun getItemCount(): Int {
        return taskList.count()
    }
}