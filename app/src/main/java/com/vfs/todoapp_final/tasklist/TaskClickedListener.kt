package com.vfs.todoapp_final.tasklist

interface TaskClickedListener {

    fun onShortClick(index : Int)
    fun onLongClick(index : Int)
}