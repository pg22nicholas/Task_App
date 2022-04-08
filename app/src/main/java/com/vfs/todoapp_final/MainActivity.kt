package com.vfs.todoapp_final

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vfs.todoapp_final.models.Category
import com.vfs.todoapp_final.models.Data
import com.vfs.todoapp_final.models.MyColor
import com.vfs.todoapp_final.tasklist.TaskFragment

class MainActivity : AppCompatActivity(), CategoryListener {

    lateinit var taskFragment : TaskFragment
    lateinit var categoryFragment: CategoryFragment

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
        taskFragment = TaskFragment.newInstance(index)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, taskFragment)
            .addToBackStack(null)
            .commit()
    }
}