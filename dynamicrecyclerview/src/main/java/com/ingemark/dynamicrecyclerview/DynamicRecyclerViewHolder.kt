package com.ingemark.dynamicrecyclerview

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.ViewDataBinding

open class DynamicRecyclerViewHolder(dataBinding: ViewDataBinding) : RecyclerView.ViewHolder(dataBinding.root) {

    lateinit var context: Context
    var item: Any? = null

    open fun onBind(context: Context, item: Any?) {
        this.item = item
        this.context = context
    }
}