package com.vfs.todoapp_final.taskedit

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.vfs.todoapp_final.R
import com.vfs.todoapp_final.models.Category
import com.vfs.todoapp_final.models.Data
import com.vfs.todoapp_final.models.MyColor
import com.vfs.todoapp_final.models.Task

private const val ARG_TASK_INDEX = "Task"
private const val ARG_CATEGORY_INDEX = "Category"

/**
 * Fragment for editing and creating tasks inside a category
 */
class EditTaskFragment : Fragment() {

    private lateinit var task : Task
    private lateinit var category : Category
    private var taskIndex : Int = 0
    private var categoryIndex : Int = 0
    private lateinit var listener : EditTaskListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            categoryIndex = it.getInt(ARG_CATEGORY_INDEX)
            category = Data.categoryList[categoryIndex]

            taskIndex = it.getInt(ARG_TASK_INDEX)

            if (taskIndex < 0) {
                //If creating a new Task inside Category
                task = Task("")
            } else {
                task = category.todoTaskList[taskIndex]

            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as EditTaskListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Spinner for priority color dropdown
        val spinner : Spinner = view.findViewById(R.id.spinner_task_priorities)
        ArrayAdapter.createFromResource(
            view.context,
            R.array.task_priority_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // layout to use for dropdown
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        // Attempt to save/create the task
        view.findViewById<Button>(R.id.button_edit_task).setOnClickListener {

            val name : String = view.findViewById<EditText>(R.id.text_edit_task).text.toString()

            // Prevent saving/creating a task with an empty name
            if (name.isEmpty()) {
                Toast.makeText(context, "Task cannot have an empty name", Toast.LENGTH_LONG).show()
            } else {
                // Save the task
                task.name = name
                    task.priorityColor = MyColor.PriorityColors.values()[spinner.selectedItemPosition]

                if (taskIndex < 0) {
                    category.addTask(task)
                    taskIndex = category.todoTaskList.size
                }
                listener.onSaveClicked(taskIndex, categoryIndex)
            }


        }

        spinner.setSelection(task.priorityColor.ordinal)
        view.findViewById<EditText>(R.id.text_edit_task).setText(task.name)
    }

    companion object {
        /**
         * @param taskIndex         Index of task to Edit, -1 if new task being created
         * @param categoryIndex     Index of category holding the task to edit/Create
         * @return A new instance of fragment EditTaskFragment.
         */
        @JvmStatic
        fun newInstance(taskIndex: Int, categoryIndex: Int) =
            EditTaskFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_TASK_INDEX, taskIndex)
                    putInt(ARG_CATEGORY_INDEX, categoryIndex)
                }
            }
    }
}