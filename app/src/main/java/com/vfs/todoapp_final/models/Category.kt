package com.vfs.todoapp_final.models

import android.util.Log
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Model for representing a Category of tasks
 */
@Serializable
class Category(val name : String, var categoryColor : MyColor.CategoryColors = MyColor.CategoryColors.DEFAULT) {

    // Tasks that are not finished
    var todoTaskList : MutableList<Task> = mutableListOf()

    // Tasks that are finished
    var finishedTaskList : MutableList<Task> = mutableListOf()

    constructor(name : String, categoryColor : MyColor.CategoryColors = MyColor.CategoryColors.DEFAULT, taskList : MutableList<Task> = mutableListOf()) : this(name, categoryColor) {
        todoTaskList = taskList.filter { !it.bDone } as MutableList<Task>
        finishedTaskList = taskList.filter { it.bDone } as MutableList<Task>
    }

    /**
     * @param task  New task to add to to-do list
     */
    fun addTask(task : Task) {
        todoTaskList.add(task)
        Data.saveData()
    }

    /**
     * Set a task as finished, moving it from to-do list to finished list
     * @param index index of task inside todoTaskList to move to finishedTaskList
     * @return      Index of task added to FinishedTaskList
     */
    fun setTaskFinished(index : Int) : Int {
        val task : Task = todoTaskList[index]
        todoTaskList.removeAt(index)
        finishedTaskList.add(task)
        Data.saveData()
        return finishedTaskList.size - 1
    }

    /**
     * Set a task as to-do, moving it from finished list to to-do list
     * @param index index of task inside finishedTaskList to move to todoTaskList
     * @return      Index of task added to todoTaskList
     */
    fun setTaskTodo(index : Int) : Int {
        val task : Task = finishedTaskList[index]
        finishedTaskList.removeAt(index)
        todoTaskList.add(task)
        Data.saveData()
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
        Data.saveData()
    }
}

