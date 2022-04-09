package com.vfs.todoapp_final.categorylist

import com.vfs.todoapp_final.EnterEditTaskListener

/**
 * Listener to communicate with Activity from Category list
 */
interface CategoryListener : EnterEditTaskListener {

    /**
     * On Category being selected, show its task content
     * @param index     Index of category selected
     */
    fun onCategorySelected(index : Int)


}