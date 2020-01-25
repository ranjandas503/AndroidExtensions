package com.sample.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.menasr.andyext.customClasses.LazyRecyclerAdapter
import com.menasr.andyext.customClasses.RecyclerAdapter
import com.menasr.andyext.extensionFunctions.visibility
import kotlinx.android.synthetic.main.layout_sample.view.*
import kotlinx.android.synthetic.main.lazy_progress.view.*

class AdapterRecycler(recyclerView: RecyclerView):
    RecyclerAdapter<CustomModel, CustomViewHolder>(recyclerView) {

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
}