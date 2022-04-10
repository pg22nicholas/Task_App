package com.vfs.todoapp_final

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vfs.todoapp_final.models.Category
import com.vfs.todoapp_final.models.Data
import android.graphics.drawable.GradientDrawable
import com.vfs.todoapp_final.categorylist.CategoryClickedListener
import com.vfs.todoapp_final.models.MyColor

/**
 * ViewHolder for displaying the list of categories
 */
class CategoryViewHolder(rootLayout : LinearLayout) : RecyclerView.ViewHolder(rootLayout) {

    var categoryNameTextView : TextView? = null
    var categoryCountTextView : TextView? = null
    var categoryHighPriorityCount : TextView? = null
    var categoryContainer : LinearLayout? = null
    var categoryRoot: LinearLayout? = null
    var addCategory : LinearLayout? = null


    init {
        categoryNameTextView = itemView.findViewById(R.id.text_category_name)
        categoryCountTextView = itemView.findViewById(R.id.text_category_task_count)
        categoryHighPriorityCount = itemView.findViewById(R.id.text_category_high_priority_count)
        categoryContainer = itemView.findViewById(R.id.category_row_container)
        categoryRoot = itemView.findViewById(R.id.category_row_root)
        addCategory = itemView.findViewById(R.id.add_category)

    }

    fun bindCategory(category: Category) {

        // display number
        categoryCountTextView?.let {
            it.text = "${category.getTaskCount()}"
        }
        // Display number of high priority tasks not finished
        categoryHighPriorityCount?.let {
            it.text = "${category.todoTaskList.filter { task -> task.priorityColor == MyColor.PriorityColors.HIGH }.size}"
        }
        categoryNameTextView?.let {
            it.text = category.name
        }

        val categoryContainerDrawable : GradientDrawable = categoryContainer?.background as GradientDrawable
        categoryContainerDrawable.setColor(category.getHexColorInt())

        val categoryRootDrawable : GradientDrawable = categoryRoot?.background as GradientDrawable
        categoryRootDrawable.setStroke(5, category.getHexColorInt())
    }
}

class CategoryAdapter(val listener : CategoryClickedListener) : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val categoryRoot = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.category_row, parent, false)
        return CategoryViewHolder(categoryRoot as LinearLayout)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = Data.categoryList[position]

        holder.categoryContainer?.setOnClickListener {
            listener.onShortClick(holder.adapterPosition)
        }

        holder.categoryContainer?.setOnLongClickListener {
            true
        }

        holder.addCategory?.setOnClickListener {
            listener.onAddClick(holder.adapterPosition)
        }

        holder.bindCategory(category)
    }

    override fun getItemCount(): Int {
        return Data.categoryList.count()
    }
}