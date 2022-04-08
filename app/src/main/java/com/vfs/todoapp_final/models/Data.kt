package com.vfs.todoapp_final.models

class Data {

    companion object {
        val categoryList : MutableList<Category> = mutableListOf();

        fun initTasks() {

            val Task1 = Task("game1", MyColor.PriorityColors.HIGH)

            val Task2 = Task("game2")
            val cat1 = Category("cat1",  mutableListOf(Task1, Task2), MyColor.CategoryColors.BLUE)
            categoryList.add(cat1)

            val Task3 = Task("game3", MyColor.PriorityColors.HIGH)
            val Task4 = Task("game4", MyColor.PriorityColors.MEDIUM)
            val Task5 = Task("game5", true)
            val cat2 = Category("cat2", mutableListOf(Task3, Task4, Task5), MyColor.CategoryColors.RED)
            categoryList.add(cat2)
        }
    }
}