package com.sample.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.menasr.andyext.customClasses.LazySwappableRecyclerAdapter

class AdapterLazySwappableRecycler(recyclerView: RecyclerView) :
    LazySwappableRecyclerAdapter<CustomModel, CustomViewHolder, LazyLoadViewHolder>(recyclerView) {

    override fun onBindHolder(holder: CustomViewHolder, data: CustomModel, position: Int) {
        holder.bindViews(data)
    }

    override fun addLayoutForParsing(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_sample,
                parent,
                false
            )
        )
    }

    override fun onBindLazyLoadHolder(
        holder: LazyLoadViewHolder,
        visibility: Boolean,
        position: Int
    ) {
        holder.bindViews(visibility)
    }

    override fun addLazyLoadingLayoutParsing(parent: ViewGroup, viewType: Int): LazyLoadViewHolder {
        return LazyLoadViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.lazy_progress,
                parent,
                false
            )
        )
    }

    override fun onSwipeRightOrLeft(
        data: CustomModel,
        position: Int,
        swipeDirection: Int
    ): Boolean {
        //Return true if you want to delete it
        return false
    }

    override fun onDragFromPosition(
        fromData: CustomModel,
        fromPosition: Int,
        toData: CustomModel,
        toPosition: Int
    ): Boolean {
        return true
    }
}