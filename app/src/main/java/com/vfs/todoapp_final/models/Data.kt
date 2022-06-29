package com.vfs.todoapp_final.models

import android.app.Application
import android.util.Log
import com.google.firebase.ktx.Firebase
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.lang.IllegalArgumentException
import java.util.regex.Pattern

/**
 * Singleton class for holding all categories and their tasks
 */
class Data {

    companion object {
        var categoryList : MutableList<Category> = mutableListOf();

        lateinit var application : Application;

        fun initiateData(application: Application) {
            this.application = application

            // Initiate starting data if task json file doesn't exist
            if (!FileStorage.isFilePresent(application)) {
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
            FirebaseData.updateOnlineData { b, s ->  }
        }

        fun saveDataFromString(jsonString: String) {
            FileStorage.create(application, jsonString)
        }


        /**
         * Add a category if its name is valid
         * @return  Data.VALID_NAME_RETURNS that details if the add was successful, or why is failed
         */
        fun addCategory(categoryToAdd : Category) : VALID_NAME_RETURNS {
            // check if name not empty and is unique
            if (!isCategoryNameUnique(categoryToAdd.name)) {
                return VALID_NAME_RETURNS.NOT_UNIQUE
            }

            var nameCheck = nameStringCheck(categoryToAdd.name)
            if (nameCheck == VALID_NAME_RETURNS.VALID) {
                categoryList.add(categoryToAdd)
                Data.saveData()
            }
            return nameCheck
        }

        /**
         * Checks if the category name is unique
         */
        private fun isCategoryNameUnique(name: String) : Boolean {
            for (cat in categoryList) {
                if (cat.name == name)
                    return false
            }
            return true
        }

        fun getCategoryIndex(categoryCheck : Category) : Int {
            for ((index, category) in categoryList.withIndex()) {
                if (category == categoryCheck)
                    return index
            }
            throw IllegalAccessException(categoryCheck.name + " is not a stored category")
        }

        /**
         * Remove a category at a specific index
         */
        fun removeCategory(categoryToDelete : Category) {
            categoryList.remove(categoryToDelete)
            Data.saveData()
        }

        /**
         * Checks if a name is not empty or has special characters
         */
        fun nameStringCheck(name : String) : VALID_NAME_RETURNS {
            val special: Pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)
            if (name.isEmpty()) {
                return VALID_NAME_RETURNS.EMPTY
            } else if (special.matcher(name).find()) {
                return VALID_NAME_RETURNS.SPECIAL_CHARACTERS
            } else {
                return VALID_NAME_RETURNS.VALID
            }
        }

        /**
         * Turns the enum of non-valid name identifiers into error strings
         * @return  error message associated with the invalid name error
         */
        fun createAddFailMessage(error : VALID_NAME_RETURNS) : String {
            return when (error) {
                VALID_NAME_RETURNS.NOT_UNIQUE -> "Name must be unique"
                VALID_NAME_RETURNS.EMPTY -> "Name cannot be empty"
                VALID_NAME_RETURNS.SPECIAL_CHARACTERS -> "Name cannot contain special characters"
                else -> {
                    throw IllegalArgumentException("No error message for a valid name")
                }
            }
        }

        fun reset() {
            categoryList = mutableListOf()
        }

    }

    enum class VALID_NAME_RETURNS {
        VALID,
        NOT_UNIQUE,
        EMPTY,
        SPECIAL_CHARACTERS
    }
}