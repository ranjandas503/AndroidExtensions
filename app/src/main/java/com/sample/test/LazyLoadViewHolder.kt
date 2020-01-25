package com.sample.test

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.menasr.andyext.extensionFunctions.visibility
import kotlinx.android.synthetic.main.lazy_progress.view.*

class LazyLoadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindViews(isProgressVisible: Boolean) {
        itemView.lazyProgressBar.visibility(isProgressVisible)
    }
}