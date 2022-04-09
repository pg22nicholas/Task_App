package com.vfs.todoapp_final.taskedit

/**
 * used to communicate with Activity to leave Fragment
 */
interface EditTaskListener {

    /**
     * Notify a task inside a specific category was updated
     * @param taskIndex     The index of the task that was inserted/updated
     * @param categoryIndex The index of the category holding the inserted/updated task
     */
    fun onSaveClicked(taskIndex : Int, categoryIndex : Int)
}