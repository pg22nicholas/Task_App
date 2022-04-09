package com.vfs.todoapp_final.categorylist

/**
 * Interface for category click events inside the category RecyclerView
 */
interface CategoryClickedListener {

    fun onShortClick(index : Int)
    fun onLongClick(index : Int)

    fun onAddClick(index : Int)
}