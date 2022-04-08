package com.vfs.todoapp_final

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vfs.todoapp_final.models.Category
import com.vfs.todoapp_final.models.Data
import android.graphics.drawable.GradientDrawable
import android.util.Log


class CategoryViewHolder(rootLayout : LinearLayout) : RecyclerView.ViewHolder(rootLayout) {

    var categoryNameTextView : TextView? = null
    var categoryCountTextView : TextView? = null
    var categoryContainer : LinearLayout? = null
    var categoryRoot: LinearLayout? = null

    init {
        categoryNameTextView = itemView.findViewById(R.id.categoryNameTextView_id)
        categoryCountTextView = itemView.findViewById(R.id.categoryCountTextView_id)
        categoryContainer = itemView.findViewById(R.id.category_row_container)
        categoryRoot = itemView.findViewById(R.id.category_row_root)
    }

    fun bindCategory(category: Category, isLast: Boolean) {

        categoryCountTextView?.let {
            it.text = "${Data.categoryList.count()}"
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

class CategoryAdapter(val listener : ClickListener) : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val categoryRoot = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.category_row, parent, false)
        return CategoryViewHolder(categoryRoot as LinearLayout)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = Data.categoryList[position]

        holder.categoryContainer?.setOnClickListener {
            listener.onShortClick(position)
        }

        holder.categoryContainer?.setOnLongClickListener {
            //listener.onLongClick(position)
            true
        }
        holder.bindCategory(category, position == Data.categoryList.count() - 1)
    }

    override fun getItemCount(): Int {
        return Data.categoryList.count()
    }
}