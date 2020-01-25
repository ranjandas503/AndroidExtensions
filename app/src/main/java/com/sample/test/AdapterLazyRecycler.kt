package com.sample.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.menasr.andyext.customClasses.LazyRecyclerAdapter
import com.menasr.andyext.extensionFunctions.visibility
import kotlinx.android.synthetic.main.layout_sample.view.*
import kotlinx.android.synthetic.main.lazy_progress.view.*

class AdapterLazyRecycler(recyclerView: RecyclerView):
    LazyRecyclerAdapter<CustomModel, CustomViewHolder, LazyLoadViewHolder>(recyclerView) {

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
}