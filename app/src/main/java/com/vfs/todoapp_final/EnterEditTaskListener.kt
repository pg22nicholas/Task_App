package com.vfs.todoapp_final

/**
 * For notifying the Activity the EditTaskFragment should be entered
 */
interface EnterEditTaskListener {

    /**
     * Go to fragment to create a new task inside category
     * @param taskIndex     Index of task to enter, -1 if inserting new task
     * @param categoryIndex Index of category to update/insert task
     */
    fun onEditTask(taskIndex : Int, categoryIndex : Int)
}