package com.vfs.todoapp_final.tasklist

/**
 * Interface for task click events inside the task RecyclerView
 */
interface TaskClickedListener {

    fun onShortClick(index : Int)
    fun onLongClick(index : Int)
}