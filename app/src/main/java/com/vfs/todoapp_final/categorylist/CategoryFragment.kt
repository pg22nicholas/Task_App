package com.vfs.todoapp_final.categorylist

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.vfs.todoapp_final.CategoryAdapter
import com.vfs.todoapp_final.R
import com.vfs.todoapp_final.models.Category
import com.vfs.todoapp_final.models.Data
import com.vfs.todoapp_final.models.MyColor




class CategoryFragment : Fragment() {

    lateinit var categoryAdapter : CategoryAdapter
    lateinit var categoryRV : RecyclerView
    lateinit var listener : CategoryListener

    lateinit var addCategoryDialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryAdapter = CategoryAdapter(categoryListener())
        categoryRV = view.findViewById(R.id.rv_category_list)
        categoryRV.adapter = categoryAdapter;


        // Alert dialog for adding new category
        val alertView: View = layoutInflater.inflate(R.layout.add_category_dialog, null)

        // Spinner for category color dropdown
        val spinner : Spinner = alertView.findViewById(R.id.spinner_category_colors)
        ArrayAdapter.createFromResource(
            view.context,
            R.array.category_color_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        addCategoryDialog = AlertDialog.Builder(activity)
            .setPositiveButton("Create", DialogInterface.OnClickListener { dialogInterface, i ->
                // get view data to create new category
                val index : Int = spinner.selectedItemPosition
                val categoryColor : MyColor.CategoryColors = MyColor.CategoryColors.values()[index]
                val nameText : EditText = alertView.findViewById(R.id.dialog_category_name)
                val name : String = nameText.text.toString()
                addNewCategory(name, categoryColor)

                // reset dialog window view values
                spinner.setSelection(0)
                nameText.setText("")
            })
            .setNegativeButton("Exit", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.cancel()
            })
            .setView(alertView)
            .create()

        view.findViewById<Button>(R.id.button_add_category).setOnClickListener {
            createAddCategoryDialog()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as CategoryListener
    }

    companion object {
        @JvmStatic
        fun newInstance() = CategoryFragment()
    }

    private fun createAddCategoryDialog() {
        addCategoryDialog.show()
    }

    private fun addNewCategory(name : String, color : MyColor.CategoryColors) {
        if (!name.isEmpty()) {
            Data.addCategory(Category(name, color))
            categoryAdapter.notifyItemInserted(Data.categoryList.size - 1)
        }
        else
            Toast.makeText(context, "Can't have empty name", Toast.LENGTH_LONG).show()
    }


    fun gotoTaskEditor() {
        // TODO:
    }
}

fun CategoryFragment.categoryListener() = object : CategoryClickedListener {

    override fun onShortClick(index: Int) {
        listener.onCategorySelected(index)
    }

    override fun onLongClick(index: Int) {
        // TODO
    }

    override fun onAddClick(index: Int) {

    }
}