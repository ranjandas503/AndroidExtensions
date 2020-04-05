package com.sample.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.menasr.andyext.customClasses.SwappableRecyclerAdapter

class AdapterSwappableRecycler(recyclerView: RecyclerView):
    SwappableRecyclerAdapter<CustomModel, CustomViewHolder>(recyclerView) {

    override fun onBindHolder(holder: CustomViewHolder, data: CustomModel, position: Int) {
        holder.bindViews(data)
    }

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_sample,
                parent,
                false
            )
        )
    }

    override fun onDragFromPosition(
        fromData: CustomModel,
        fromPosition: Int,
        toData: CustomModel,
        toPosition: Int
    ): Boolean {
        return true
    }

    override fun onSwipeRightOrLeft(
        data: CustomModel,
        position: Int,
        swipeDirection: Int
    ): Boolean {
        return true
    }
}