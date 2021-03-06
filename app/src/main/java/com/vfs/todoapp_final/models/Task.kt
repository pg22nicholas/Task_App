package com.vfs.todoapp_final.models

import kotlinx.serialization.Serializable
import kotlin.properties.Delegates

/**
 * Model representing a single task
 */
@Serializable
class Task(var name : String) {

    var priorityColor : MyColor.PriorityColors = MyColor.PriorityColors.NONE

    var bDone = false

    constructor(name: String, bDone : Boolean = false) : this(name) {
        this.bDone = bDone
        this.priorityColor = MyColor.PriorityColors.NONE
    }

    constructor(name: String, priorityColor : MyColor.PriorityColors, bDone : Boolean = false) : this(name) {
        this.bDone = bDone
        this.priorityColor = priorityColor
    }

    fun updateTask(name: String, priorityColor : MyColor.PriorityColors) {
        this.name = name
        this.priorityColor = priorityColor
        Data.saveData()
    }



    fun flipSelected() : Boolean {
        bDone = !bDone
        return bDone
    }

    /**
     * Get the hex value of the category color as an int
     * @return  int color hex value
     */
    fun getHexColorInt() : Int {
        return MyColor.getPriorityHexColorInt(priorityColor)
    }
}