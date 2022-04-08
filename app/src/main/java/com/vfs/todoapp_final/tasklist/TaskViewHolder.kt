package com.vfs.todoapp_final.tasklist

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vfs.todoapp_final.R
import com.vfs.todoapp_final.models.Task

abstract class TaskViewHolder(rootLayout : LinearLayout) : RecyclerView.ViewHolder(rootLayout) {

    var taskNameTextView : TextView? = null
    var taskCheckbox : CheckBox? = null
    var taskContainer : LinearLayout? = null
    var taskRowRoot : LinearLayout? = null

    init {
        taskNameTextView = itemView.findViewById(R.id.taskNameTextView_id)
        taskCheckbox = itemView.findViewById(R.id.taskCheckbox_id)
        taskContainer = itemView.findViewById(R.id.task_container)
        taskRowRoot = itemView.findViewById(R.id.task_row_root)
    }

    abstract fun bindCategory(task: Task, isLast: Boolean)

    fun setPriorityColors(task : Task) {
        val taskDrawable : GradientDrawable = taskRowRoot?.background as GradientDrawable
        taskDrawable.setStroke(6, task.getHexColorInt())
    }
}