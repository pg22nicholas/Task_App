package com.vfs.todoapp_final

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vfs.todoapp_final.models.Category
import com.vfs.todoapp_final.models.Data

class CategoryFragment : Fragment() {

    lateinit var categoryAdapter : CategoryAdapter
    lateinit var categoryRV : RecyclerView
    lateinit var listener : CategoryListener

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
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as CategoryListener
    }

    companion object {
        @JvmStatic
        fun newInstance() = CategoryFragment()
    }
}

fun CategoryFragment.categoryListener() = object : ClickListener {

    override fun onShortClick(index: Int) {
        listener.onCategorySelected(index)
    }

    override fun onLongClick(index: Int) {
        TODO("Not yet implemented")
    }
}