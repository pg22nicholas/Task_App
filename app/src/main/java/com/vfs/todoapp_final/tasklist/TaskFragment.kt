package com.vfs.todoapp_final.tasklist

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.vfs.todoapp_final.R
import com.vfs.todoapp_final.models.Category
import com.vfs.todoapp_final.models.Data
import com.vfs.todoapp_final.models.MyColor

/**
 * Fragment for displaying a list of tasks inside a category
 */
class TaskFragment : Fragment() {

    val ARG_CATEGORY : String = "category"

    lateinit var todoTaskAdapter : TaskAdapter
    lateinit var finishedTaskAdapter : TaskAdapter
    lateinit var taskListener : TaskListener
    lateinit var taskbarMenu : Menu

    lateinit var selectedCategory: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val categoryIndex : Int = it.getInt(ARG_CATEGORY)
            selectedCategory = Data.categoryList[categoryIndex]
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RV for to-do tasks
        todoTaskAdapter = TaskAdapter(selectedCategory, todoTaskListener(), true)
        val todoRV : RecyclerView = view.findViewById(R.id.rv_todo_task_list)
        todoRV.adapter = todoTaskAdapter

        // Setup RV for finished tasks
        finishedTaskAdapter = TaskAdapter(selectedCategory, finishedTaskListener(), false)
        val finishedRV : RecyclerView = view.findViewById(R.id.rv_finished_task_list)
        finishedRV.adapter = finishedTaskAdapter

        // Removes all finished tasks
        view.findViewById<Button>(R.id.button_remove_finished_tasks).setOnClickListener {
            selectedCategory.removeAllFinishedTasks()
            finishedTaskAdapter.notifyDataSetChanged()
        }

        // Create a new task
        view.findViewById<Button>(R.id.button_add_task).setOnClickListener {
            taskListener.onEditTask(-1, Data.getCategoryIndex(selectedCategory))
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        taskListener = context as TaskListener
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.task_list_toolbar_menu, menu)
        taskbarMenu = menu
        setMenuItemColor()
    }

    private fun setMenuItemColor() {
        val colorPickerDrawable : Drawable = taskbarMenu.findItem(R.id.category_menu_color_picker).icon
        colorPickerDrawable.setTint(MyColor.getCategoryHexColorInt(selectedCategory.categoryColor))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.task_list_sort_priority -> {
                sortListByPriority()
            }
            R.id.task_list_sort_alphabetical_ascending -> {
                sortListByAlphabetical(true)
            }
            R.id.task_list_sort_alphabetical_descending -> {
                sortListByAlphabetical(false)
            }
            R.id.category_menu_delete -> {
                deleteCategory()
            }
            R.id.category_menu_color_default -> {
                onNewColorSelected(MyColor.CategoryColors.DEFAULT)
            }
            R.id.category_menu_color_red -> {
                onNewColorSelected(MyColor.CategoryColors.RED)
            }
            R.id.category_menu_color_blue -> {
                onNewColorSelected(MyColor.CategoryColors.BLUE)
            }
            R.id.category_menu_color_yellow -> {
                onNewColorSelected(MyColor.CategoryColors.YELLOW)
            }
            R.id.category_menu_color_green -> {
                onNewColorSelected(MyColor.CategoryColors.GREEN)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun onNewColorSelected(color : MyColor.CategoryColors) {
        selectedCategory.categoryColor = color
        setMenuItemColor()
    }

    private fun deleteCategory() {

        AlertDialog.Builder(activity)
            .setMessage("Do you want to delete " + selectedCategory.name + "?")
            .setCancelable(true)
            .setPositiveButton("Delete", DialogInterface.OnClickListener { dialogInterface, i ->
                Data.categoryList.removeAt(Data.getCategoryIndex(selectedCategory))
                taskListener.onCategoryDeleted()
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.cancel()
            })
            .create()
            .show()
    }

    private fun sortListByPriority() {
        selectedCategory.todoTaskList.sortByDescending { it.priorityColor }
        selectedCategory.finishedTaskList.sortByDescending { it.priorityColor }
        finishedTaskAdapter.notifyDataSetChanged()
        todoTaskAdapter.notifyDataSetChanged()
    }

    private fun sortListByAlphabetical(isAscending : Boolean) {
        if (isAscending) {
            selectedCategory.todoTaskList.sortBy { it.name }
            selectedCategory.finishedTaskList.sortBy { it.name }
        } else {
            selectedCategory.todoTaskList.sortByDescending { it.name }
            selectedCategory.finishedTaskList.sortByDescending { it.name }
        }

        finishedTaskAdapter.notifyDataSetChanged()
        todoTaskAdapter.notifyDataSetChanged()
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

    // Set to-do task as finished
    override fun onShortClick(index: Int) {
        val insertIndex = selectedCategory.setTaskFinished(index)
        todoTaskAdapter.notifyItemRemoved(index)
        finishedTaskAdapter.notifyItemInserted(insertIndex)
    }

    // Go to edit fragment for the task
    override fun onLongClick(index: Int) {
        taskListener.onEditTask(index, Data.getCategoryIndex(selectedCategory))
    }
}



fun TaskFragment.finishedTaskListener() = object : TaskClickedListener {

    // Set finished task as to-do
    override fun onShortClick(index: Int) {
        val insertIndex = selectedCategory.setTaskTodo(index)
        finishedTaskAdapter.notifyItemRemoved(index)
        todoTaskAdapter.notifyItemInserted(insertIndex)
    }

    override fun onLongClick(index: Int) {
        // TODO
    }
}