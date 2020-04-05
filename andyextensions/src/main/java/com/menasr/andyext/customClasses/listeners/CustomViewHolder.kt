package com.menasr.andyext.customClasses.listeners

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/** This is a custom class for view holder, This binds the data,
 * Here @param DATA_BINDING_CLASS is your LayoutBindingClass i.e., R.layout.<layout_id> binding class(Ex: AdapterRecyclerBinding)
 * And @param MODEL_CLASS is your model class, or a single object which you want to bind
 *
 * You will get binding object by default which is <b>"mBinding"</b>*/
abstract class CustomViewHolder<MODEL_CLASS, DATA_BINDING_CLASS : ViewDataBinding>(itemView: DATA_BINDING_CLASS) :
    RecyclerView.ViewHolder(itemView.root) {
    internal val mBinding: DATA_BINDING_CLASS = itemView

    /**Pass your binding function here*/
    fun bindView(function: () -> Unit) {
        function()
    }
}