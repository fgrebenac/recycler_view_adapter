package com.recyclerviewadapter

import android.content.Context
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class RecyclerViewHolder(dataBinding: ViewDataBinding) : RecyclerView.ViewHolder(dataBinding.root) {

    lateinit var context: Context
    var item: Any? = null

    open fun onBind(context: Context, item: Any?) {
        this.item = item
        this.context = context
    }
}