package com.vfs.todoapp_final

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.vfs.todoapp_final.categorylist.CategoryFragment
import com.vfs.todoapp_final.categorylist.CategoryListener
import com.vfs.todoapp_final.models.Data
import com.vfs.todoapp_final.models.MyColor
import com.vfs.todoapp_final.taskedit.EditTaskFragment
import com.vfs.todoapp_final.taskedit.EditTaskListener
import com.vfs.todoapp_final.tasklist.TaskFragment
import com.vfs.todoapp_final.tasklist.TaskListener
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import androidx.annotation.NonNull




/**
 * Main Activity for displaying Categories and their tasks
 */
class MainActivity : AppCompatActivity(), CategoryListener, EditTaskListener, TaskListener {

    lateinit var taskFragment : TaskFragment
    lateinit var categoryFragment: CategoryFragment
    lateinit var editTaskFragment : EditTaskFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Data.initiateData(application)
        MyColor.initiateColors(application)

        categoryFragment = CategoryFragment.newInstance();
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, categoryFragment)
            .commit()
    }

    override fun onCategorySelected(index: Int) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Category: " + Data.categoryList[index].name

        // create and start task list fragment
        taskFragment = TaskFragment.newInstance(index)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            .replace(R.id.fragment_container, taskFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCategoryDeleted() {
        supportFragmentManager.popBackStack()
        supportActionBar?.title = resources.getString(R.string.app_name)
    }

    override fun onEditTask(taskIndex: Int, categoryIndex: Int) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (taskIndex < 0)
            supportActionBar?.title = "Add Task"
        else
            supportActionBar?.title = "Edit Task"

        // Create and start edit task fragment
        editTaskFragment = EditTaskFragment.newInstance(taskIndex, categoryIndex)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            .replace(R.id.fragment_container, editTaskFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportActionBar?.title = resources.getString(R.string.app_name)
        checkShowHomeUpButton()
    }

    override fun onSaveClicked(taskIndex: Int, categoryIndex: Int) {
        supportFragmentManager.popBackStack()
        supportActionBar?.title = resources.getString(R.string.app_name)
        checkShowHomeUpButton()
    }

    // Hide HomeUp button if category list is visible
    private fun checkShowHomeUpButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(!categoryFragment.isVisible)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                super.onBackPressed()
                checkShowHomeUpButton()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}