package com.vfs.todoapp_final.models

import android.app.Application
import android.util.Log
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Singleton class for holding all categories and their tasks
 */
class Data {

    companion object {
        var categoryList : MutableList<Category> = mutableListOf();

        lateinit var application : Application;

        fun initiateData(application: Application) {
            this.application = application

            // Initiate starting data if task json file doesnt exist
            if (!FileStorage.isFilePresent(application)) {
                val Task1 = Task("game1", MyColor.PriorityColors.HIGH)

                val Task2 = Task("game2")
                val cat1 = Category("cat1", MyColor.CategoryColors.BLUE, mutableListOf(Task1, Task2))
                categoryList.add(cat1)

                val Task3 = Task("game3", MyColor.PriorityColors.HIGH)
                val Task4 = Task("game4", MyColor.PriorityColors.MEDIUM)
                val Task5 = Task("game5", true)
                val cat2 = Category("cat2", MyColor.CategoryColors.RED, mutableListOf(Task3, Task4, Task5))
                categoryList.add(cat2)

                FileStorage.create(application, Json.encodeToString(categoryList))
                Log.i("storage", "stored data")
                // otherwise, retrieve existing data
            } else {
                FileStorage.read(application)?.let {
                    Log.i("storage", it)
                    categoryList = Json.decodeFromString<MutableList<Category>>(it)
                }
            }
        }

        fun saveData() {
            FileStorage.create(application, Json.encodeToString(categoryList))
        }

        fun addCategory(categoryToAdd : Category) {
            categoryList.add(categoryToAdd)
        }

        fun getCategoryIndex(categoryCheck : Category) : Int {
            var index : Int = 0;
            for (category in categoryList) {
                if (category == categoryCheck)
                    return index
                index++
            }
            throw IllegalAccessException(categoryCheck.name + " is not a stored category")
        }
    }
}