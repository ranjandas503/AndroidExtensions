package com.menasr.andy

import android.os.Handler
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

const val VIEW_TYPE_ITEM = 0
const val VIEW_TYPE_LOADING = 1

abstract class LazyRecyclerAdapter<T, U : RecyclerView.ViewHolder, V : RecyclerView.ViewHolder>(
    private val recyclerView: RecyclerView
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: MutableList<T> = ArrayList()
    private var progressVisibility = false
    private var canLoadAgain: Boolean = true
    private var listener: LazyLoadRecyclerCallback? = null

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
    fun addItems(list: List<T>) {
        Handler().postDelayed({
            val oldPos = list.size
            this.list.addAll(list)
            progressVisibility = false
            if (!recyclerView.isComputingLayout)
                notifyItemInserted(oldPos)
        }, 1000)
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
            onBindHolder(holder as U, list[position], position)
        } else {
            //default size for now, later from api
            if (canLoadAgain && list.isNotEmpty() && canClickAgain()) {
                progressVisibility = true //this variable can be removed
                listener?.onLoadMore()
            } else progressVisibility = false

            @Suppress("UNCHECKED_CAST")
            onBindLazyLoadHolder(holder as V, progressVisibility, position)
        }
    }

    abstract fun onBindHolder(holder: U, data: T, position: Int)
    abstract fun onBindLazyLoadHolder(holder: V, visibility: Boolean, position: Int)
    abstract fun addLayoutForParsing(parent: ViewGroup, viewType: Int): U
    abstract fun addLazyLoadingLayoutParsing(parent: ViewGroup, viewType: Int): V

    /**Responsible for lazy loading in recycler view items
     * #onLoadMore will be invoked when user scrolls to end,
     * remember to check you have this implemented*/
    interface LazyLoadRecyclerCallback {
        fun onLoadMore()
    }
}