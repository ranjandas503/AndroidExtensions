package com.sample.test

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_sample.view.*

class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindViews(customModel: CustomModel) {
        itemView.let {
            it.tv1.text = customModel.firstName
            it.tv2.text = customModel.lastName
        }
    }
}