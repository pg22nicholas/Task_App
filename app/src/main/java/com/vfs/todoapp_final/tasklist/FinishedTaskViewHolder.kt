package com.vfs.todoapp_final.tasklist

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.widget.LinearLayout
import com.vfs.todoapp_final.models.Task

class FinishedTaskViewHolder(rootLayout : LinearLayout) : TaskViewHolder(rootLayout) {

    override fun bindCategory(task: Task, isLast: Boolean) {

        taskNameTextView?.let {
            it.text = task.name
        }

        taskCheckbox?.let {
            it.isChecked = true
        }

        taskRowRoot?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#C6C6C6"))
        taskNameTextView?.let {
            it.paintFlags = it.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }

        setPriorityColors(task)
    }
}