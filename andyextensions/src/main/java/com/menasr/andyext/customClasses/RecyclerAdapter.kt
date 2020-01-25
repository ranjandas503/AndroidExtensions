@file:Suppress("unused")

package com.menasr.andyext.customClasses

import android.os.Handler
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerAdapter<MODEL_CLASS, VH_CLASS : RecyclerView.ViewHolder>(private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<VH_CLASS>() {

    private var list: MutableList<MODEL_CLASS> = ArrayList()

    /**Get actual list*/
    fun getList() = list

    /**Every list of items added will be reflected after 1 sec*/
    fun addItem(list: List<MODEL_CLASS>) {
        Handler().postDelayed({
            val oldPos = list.size
            this.list.addAll(list)
            if (!recyclerView.isComputingLayout)
                notifyItemInserted(oldPos)
        }, 1000)
    }

    /**Remove the item from the list*/
    fun removeItem(item : MODEL_CLASS) = removeItem(list.indexOf(item))

    /**Remove the item from the list with position*/
    @Suppress("MemberVisibilityCanBePrivate")
    fun removeItem(position: Int){
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    /**Add single item to the adapter,
     * @param item to be added
     * @param position send the position to add the item, else it will be added in last*/
    fun addItem(item: MODEL_CLASS, position: Int? = null) {
        Handler().postDelayed({
            val oldPos = list.size
            if (position == null) list.add(item)
            else list.add(position, item)
            if (!recyclerView.isComputingLayout)
                notifyItemInserted(position ?: oldPos)
        }, 500)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = onCreateHolder(parent, viewType)
    override fun getItemCount() = list.size
    override fun onBindViewHolder(holder: VH_CLASS, position: Int) = onBindHolder(holder,list[position], position)


    abstract fun onBindHolder(holder: VH_CLASS, data: MODEL_CLASS, position: Int)
    abstract fun onCreateHolder(parent: ViewGroup, viewType: Int): VH_CLASS

}