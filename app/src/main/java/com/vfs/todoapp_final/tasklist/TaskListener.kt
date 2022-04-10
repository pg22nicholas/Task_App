package com.vfs.todoapp_final.tasklist

import com.vfs.todoapp_final.EnterEditTaskListener

/**
 * Listener to communicate with Activity from Task list
 */
interface TaskListener : EnterEditTaskListener {

    fun onCategoryDeleted()
}