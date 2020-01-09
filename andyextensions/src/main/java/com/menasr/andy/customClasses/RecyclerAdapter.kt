package com.menasr.andy.customClasses

import android.os.Handler
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerAdapter<T, U : RecyclerView.ViewHolder>(private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<U>() {

    private var list: MutableList<T> = ArrayList()

    /**Get actual list*/
    fun getList() = list

    /**Every list of items added will be reflected after 1 sec*/
    fun addItem(list: List<T>) {
        Handler().postDelayed({
            val oldPos = list.size
            this.list.addAll(list)
            if (!recyclerView.isComputingLayout)
                notifyItemInserted(oldPos)
        }, 1000)
    }

    /**Add single item to the adapter,
     * @param item to be added
     * @param position send the position to add the item, else it will be added in last*/
    fun addItem(item: T, position: Int? = null) {
        Handler().postDelayed({
            val oldPos = list.size
            if (position == null) list.add(item)
            else list.add(position, item)
            if (!recyclerView.isComputingLayout)
                notifyItemInserted(position ?: oldPos)
        }, 500)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        onCreateHolder(parent, viewType)

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: U, position: Int) = onBindHolder(holder, position)

    abstract fun onBindHolder(holder: U, position: Int)
    abstract fun onCreateHolder(parent: ViewGroup, viewType: Int): U

}