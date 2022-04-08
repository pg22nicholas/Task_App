package com.vfs.todoapp_final.tasklist

import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.widget.LinearLayout
import com.vfs.todoapp_final.models.Task

class TodoTaskViewHolder (rootLayout : LinearLayout) : TaskViewHolder(rootLayout) {

    override fun bindCategory(task: Task, isLast: Boolean) {

        taskNameTextView?.let {
            it.text = task.name
        }

        taskCheckbox?.let {
            it.isChecked = false
        }

        taskNameTextView?.let {
            it.paintFlags = it.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        setPriorityColors(task)
    }
}