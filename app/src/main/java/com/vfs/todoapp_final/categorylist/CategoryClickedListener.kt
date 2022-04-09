package com.vfs.todoapp_final.categorylist

// Interface for Click events
interface CategoryClickedListener {

    fun onShortClick(index : Int)
    fun onLongClick(index : Int)

    fun onAddClick(index : Int)
}