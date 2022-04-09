package com.vfs.todoapp_final.tasklist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.vfs.todoapp_final.R
import com.vfs.todoapp_final.models.Category
import com.vfs.todoapp_final.models.Data

class TaskFragment : Fragment() {

    val ARG_CATEGORY : String = "category"

    lateinit var todoTaskAdapter : TaskAdapter
    lateinit var finishedTaskAdapter : TaskAdapter
    lateinit var taskListener : TaskListener

    lateinit var selectedCategory: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val categoryIndex : Int = it.getInt(ARG_CATEGORY)
            selectedCategory = Data.categoryList[categoryIndex]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        taskListener = context as TaskListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todoTaskAdapter = TaskAdapter(selectedCategory, todoTaskListener(), true)
        val todoRV : RecyclerView = view.findViewById(R.id.rv_todo_task_list)
        todoRV.adapter = todoTaskAdapter

        finishedTaskAdapter = TaskAdapter(selectedCategory, finishedTaskListener(), false)
        val finishedRV : RecyclerView = view.findViewById(R.id.rv_finished_task_list)
        finishedRV.adapter = finishedTaskAdapter

        view.findViewById<Button>(R.id.button_remove_finished_tasks).setOnClickListener {
            selectedCategory.removeAllFinishedTasks()
            finishedTaskAdapter.notifyDataSetChanged()
        }

        view.findViewById<Button>(R.id.button_add_task).setOnClickListener {

            taskListener.onEditTask(-1, Data.getCategoryIndex(selectedCategory))

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(selectedCategoryIndex : Int) =
            TaskFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CATEGORY, selectedCategoryIndex)
                }
            }
    }
}

fun TaskFragment.todoTaskListener() = object : TaskClickedListener {

    override fun onShortClick(index: Int) {
        todoTaskAdapter.notifyItemRemoved(index)
        val insertIndex = selectedCategory.setTaskFinished(index)
        finishedTaskAdapter.notifyItemInserted(insertIndex)
    }

    override fun onLongClick(index: Int) {
        taskListener.onEditTask(index, Data.getCategoryIndex(selectedCategory))
    }
}

fun TaskFragment.finishedTaskListener() = object : TaskClickedListener {

    override fun onShortClick(index: Int) {
        finishedTaskAdapter.notifyItemRemoved(index)
        val insertIndex = selectedCategory.setTaskTodo(index)
        todoTaskAdapter.notifyItemInserted(insertIndex)
    }

    override fun onLongClick(index: Int) {
        // TODO
    }
}