@file:Suppress("unused")

package com.menasr.andyext.customClasses

import android.os.Handler
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.menasr.andyext.customClasses.listeners.ItemTouchHelperAdapter
import com.menasr.andyext.customClasses.listeners.OnStartDragListener
import com.menasr.andyext.customClasses.listeners.SimpleItemTouchHelperCallback
import com.menasr.andyext.extensionFunctions.canClickAgain
import java.util.*
import kotlin.collections.ArrayList

const val VIEW_TYPE_ITEM = 0
const val VIEW_TYPE_LOADING = 1

abstract class LazyRecyclerAdapter<MODEL_CLASS, DATA_VH_CLASS : RecyclerView.ViewHolder, LAZYLOAD_VH_CLASS : RecyclerView.ViewHolder>(
    private val recyclerView: RecyclerView
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperAdapter, OnStartDragListener {

    private var list: MutableList<MODEL_CLASS> = ArrayList()
    private var progressVisibility = false
    private var canLoadAgain: Boolean = true
    private var listener: LazyLoadRecyclerCallback? = null

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

    /**Every update on the value will reflected after 500ms*/
    fun canLazyLoadAgain(canLoadAgain: Boolean = true) {
        this.canLoadAgain = canLoadAgain
        Handler().postDelayed({
            if (!recyclerView.isComputingLayout)
                notifyItemChanged(list.size)
        }, 500)
    }

    fun addLazyLoadCallback(listener: LazyLoadRecyclerCallback) {
        this.listener = listener
    }

    /**Every list of items added will be reflected after 1 sec*/
    fun addItem(list: List<MODEL_CLASS>) {
        Handler().postDelayed({
            val oldPos = list.size
            this.list.addAll(list)
            progressVisibility = false
            if (!recyclerView.isComputingLayout)
                notifyItemInserted(oldPos)
        }, 1000)
    }

    /**Add single item to the adapter,
     * @param item to be added
     * @param position send the position to add the item, else it will be added in last*/
    fun addItem(item: MODEL_CLASS, position: Int? = null) {
        Handler().postDelayed({
            val oldPos = list.size
            if (position == null)
                list.add(item)
            else list.add(position, item)
            if (!recyclerView.isComputingLayout)
                notifyItemInserted(position ?: oldPos)
        }, 500)
    }

    /**Remove the item from the list*/
    fun removeItem(item: MODEL_CLASS) = removeItem(list.indexOf(item))

    /**Remove the item from the list with position*/
    @Suppress("MemberVisibilityCanBePrivate")
    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM)
            addLayoutForParsing(parent, viewType)
        else
            addLazyLoadingLayoutParsing(parent, viewType)
    }

    override fun getItemCount(): Int = list.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (isPositionFooter(position)) VIEW_TYPE_LOADING
        else VIEW_TYPE_ITEM
    }

    //If position is last then type is footer
    private fun isPositionFooter(position: Int): Boolean {
        return position == list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            @Suppress("UNCHECKED_CAST")
            onBindHolder(holder as DATA_VH_CLASS, list[position], position)
        } else {
            //default size for now, later from api
            if (canLoadAgain && list.isNotEmpty() && canClickAgain()) {
                progressVisibility = true //this variable can be removed
                listener?.onLoadMore()
            } else progressVisibility = false

            @Suppress("UNCHECKED_CAST")
            onBindLazyLoadHolder(holder as LAZYLOAD_VH_CLASS, progressVisibility, position)
        }
    }

    abstract fun onBindHolder(holder: DATA_VH_CLASS, data: MODEL_CLASS, position: Int)
    abstract fun onBindLazyLoadHolder(holder: LAZYLOAD_VH_CLASS, visibility: Boolean, position: Int)
    abstract fun addLayoutForParsing(parent: ViewGroup, viewType: Int): DATA_VH_CLASS
    abstract fun addLazyLoadingLayoutParsing(parent: ViewGroup, viewType: Int): LAZYLOAD_VH_CLASS

    /**Return true if  you want to delete the data in left or right swipe else return false.
     * Check swipe direction as [ItemTouchHelper] is used for touch detection*/
    abstract fun onSwipeRightOrLeft(data: MODEL_CLASS, position: Int, swipeDirection: Int): Boolean

    /**Return true if  you want to swap the position else return false*/
    abstract fun onDragFromPosition(
        fromData: MODEL_CLASS,
        fromPosition: Int,
        toData: MODEL_CLASS,
        toPosition: Int
    ): Boolean

    /**Responsible for lazy loading in recycler view items
     * #onLoadMore will be invoked when user scrolls to end,
     * remember to check you have this implemented*/
    interface LazyLoadRecyclerCallback {
        fun onLoadMore()
    }
}