@file:Suppress("unused")

package com.menasr.andyext.customClasses

import android.os.Handler
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.menasr.andyext.customClasses.listeners.ItemTouchHelperAdapter
import com.menasr.andyext.customClasses.listeners.OnStartDragListener
import com.menasr.andyext.customClasses.listeners.SimpleItemTouchHelperCallback
import java.util.*
import kotlin.collections.ArrayList

abstract class RecyclerAdapter<MODEL_CLASS, VH_CLASS : RecyclerView.ViewHolder>(private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<VH_CLASS>(), ItemTouchHelperAdapter, OnStartDragListener {

    private var list: MutableList<MODEL_CLASS> = ArrayList()

    private var mItemTouchHelper: ItemTouchHelper? = null
    private var cursorPosition: Int = 0

    init {
        canSwapOrDrag(false)
    }

    private fun setSwipeListener() {
        val callback = SimpleItemTouchHelperCallback(this)
        mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper?.attachToRecyclerView(recyclerView)
    }

    fun canSwapOrDrag(isSwappable: Boolean) {
        if (isSwappable)
            setSwipeListener()
        else {
            mItemTouchHelper = null
            cursorPosition = 0
        }
    }

    override fun onItemDrag(fromPosition: Int, toPosition: Int): Boolean {
        //changing cursor position on dragging
        cursorPosition = toPosition

        //Do something on dragging two items
        return if (onDragFromPosition(
                list[fromPosition],
                fromPosition,
                list[toPosition],
                toPosition
            )
        ) {
            //swapping item
            Collections.swap(list, fromPosition, toPosition)
            notifyItemMoved(fromPosition, toPosition)
            true
        } else false
    }

    override fun onItemDismiss(position: Int, swipeDirection: Int) {
        if (onSwipeRightOrLeft(list[position], position, swipeDirection)) {
            list.removeAt(position)
            notifyItemRemoved(position)
        } else notifyItemChanged(position)
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        mItemTouchHelper?.startDrag(viewHolder)
    }

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
    /**Return true if  you want to swap the position else return false*/
    abstract fun onDragFromPosition(fromData: MODEL_CLASS, fromPosition: Int, toData: MODEL_CLASS, toPosition: Int): Boolean
    /**Return true if  you want to delete the data in left or right swipe else return false.
     * Check swipe direction as [ItemTouchHelper] is used for touch detection*/
    abstract fun onSwipeRightOrLeft(data: MODEL_CLASS, position: Int, swipeDirection: Int): Boolean
}