package com.sample.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.menasr.andyext.customClasses.listeners.CustomRecycler

class MyCustomAdapter(recylerView:RecyclerView):CustomRecycler<CustomModel, CustomViewHolder,LazyLoadViewHolder>(recylerView) {
    override fun onBindHolder(holder: CustomViewHolder, data: CustomModel, position: Int) {
        holder.bindViews(data)
    }

    override fun onBindLazyLoadHolder(
        holder: LazyLoadViewHolder,
        visibility: Boolean,
        position: Int
    ) {
        holder.bindViews(visibility)
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

    override fun addLazyLoadingLayoutParsing(parent: ViewGroup, viewType: Int): LazyLoadViewHolder {
        return LazyLoadViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.lazy_progress,
                parent,
                false
            )
        )
    }

}