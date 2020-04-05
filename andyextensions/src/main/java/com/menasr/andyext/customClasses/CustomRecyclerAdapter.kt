@file:Suppress("unused")

package com.menasr.andyext.customClasses

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**Custom Recyclerview Adapter class in place of Recyclerview.Adapter*/
abstract class CustomRecyclerAdapter<MODEL_CLASS, VH_CLASS : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<VH_CLASS>() {

    private var list: MutableList<MODEL_CLASS> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH_CLASS =
        onCreateHolder(parent, viewType)

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: VH_CLASS, position: Int) =
        onBindHolder(holder, list[position], position)

    fun addItems(list: List<MODEL_CLASS>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun addItems(item: MODEL_CLASS) {
        list.add(item)
        notifyItemChanged(list.indexOf(item))
    }

    fun removeItems(item: MODEL_CLASS) {
        val index = list.indexOf(item)
        list.removeAt(index)
        notifyItemChanged(index)
    }

    /**Provide your view here*/
    abstract fun onCreateHolder(parent: ViewGroup, viewType: Int): VH_CLASS

    /**Provide your each layout operation here*/
    abstract fun onBindHolder(holder: VH_CLASS, data: MODEL_CLASS, position: Int)
}