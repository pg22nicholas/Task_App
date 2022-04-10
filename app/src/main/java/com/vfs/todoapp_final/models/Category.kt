package com.vfs.todoapp_final.models

/**
 * Model for representing a Category of tasks
 */
class Category(val name : String, var categoryColor : MyColor.CategoryColors = MyColor.CategoryColors.DEFAULT, taskList : MutableList<Task> = mutableListOf()) {

    // Tasks that are not finished
    var todoTaskList : MutableList<Task> = taskList.filter { !it.bDone } as MutableList<Task>

    // Tasks that are finished
    var finishedTaskList : MutableList<Task> = taskList.filter { it.bDone } as MutableList<Task>

    /**
     * @param task  New task to add to to-do list
     */
    fun addTask(task : Task) {
        todoTaskList.add(task)
    }

    /**
     * @param index index of task inside todoTaskList to move to finishedTaskList
     * @return      Index of task added to FinishedTaskList
     */
    fun setTaskFinished(index : Int) : Int {
        val task : Task = todoTaskList[index]
        todoTaskList.removeAt(index)
        finishedTaskList.add(task)
        return finishedTaskList.size - 1
    }

    /**
     * @param index index of task inside finishedTaskList to move to todoTaskList
     * @return      Index of task added to todoTaskList
     */
    fun setTaskTodo(index : Int) : Int {
        val task : Task = finishedTaskList[index]
        finishedTaskList.removeAt(index)
        todoTaskList.add(task)
        return todoTaskList.size - 1
    }

    fun getTaskCount() : Int {
        return todoTaskList.size + finishedTaskList.size
    }

    /**
     * Get the hex value of the category color as an int
     * @return  int color hex value
     */
    fun getHexColorInt() : Int {
        return MyColor.getCategoryHexColorInt(categoryColor)
    }

    fun removeAllFinishedTasks() {
        finishedTaskList.clear()
    }
}

