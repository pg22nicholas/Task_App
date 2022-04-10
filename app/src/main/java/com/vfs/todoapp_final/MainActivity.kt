package com.vfs.todoapp_final

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.vfs.todoapp_final.categorylist.CategoryFragment
import com.vfs.todoapp_final.categorylist.CategoryListener
import com.vfs.todoapp_final.models.Data
import com.vfs.todoapp_final.models.MyColor
import com.vfs.todoapp_final.taskedit.EditTaskFragment
import com.vfs.todoapp_final.taskedit.EditTaskListener
import com.vfs.todoapp_final.tasklist.TaskFragment
import com.vfs.todoapp_final.tasklist.TaskListener

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

        Data.initTasks()
        MyColor.initiateColors(application)

        categoryFragment = CategoryFragment.newInstance();
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, categoryFragment)
            .commit()

    }

    override fun onCategorySelected(index: Int) {
        supportActionBar?.title = "Category: " + Data.categoryList[index].name

        // create and start task list fragment
        taskFragment = TaskFragment.newInstance(index)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, taskFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCategoryDeleted() {
        supportFragmentManager.popBackStack()
        supportActionBar?.title = resources.getString(R.string.app_name)
    }

    override fun onEditTask(taskIndex: Int, categoryIndex: Int) {
        if (taskIndex < 0)
            supportActionBar?.title = "Add Task"
        else
            supportActionBar?.title = "Edit Task"

        // Create and start edit task fragment
        editTaskFragment = EditTaskFragment.newInstance(taskIndex, categoryIndex)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, editTaskFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportActionBar?.title = resources.getString(R.string.app_name)
    }

    override fun onSaveClicked(taskIndex: Int, categoryIndex: Int) {
        supportFragmentManager.popBackStack()
        supportActionBar?.title = resources.getString(R.string.app_name)
    }
}