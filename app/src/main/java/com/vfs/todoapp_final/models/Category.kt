package com.vfs.todoapp_final.models

class Category(val name : String, taskList : MutableList<Task>, private var categoryColor : MyColor.CategoryColors = MyColor.CategoryColors.DEFAULT) {

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

    /**
     * Get the hex value of the category color as an int
     * @return  int color hex value
     */
    fun getHexColorInt() : Int {
        return MyColor.getCategoryHexColorInt(categoryColor)
    }
}

