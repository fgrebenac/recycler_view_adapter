package com.ingemark.dynamicrecyclerview

import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import java.util.regex.Pattern

class DynamicRecyclerViewAdapter(private val context: Context, private val previewLength: Int = 0) : RecyclerView.Adapter<DynamicRecyclerViewHolder>() {

    private var itemList: MutableList<DynamicRecyclerViewHolderBuilder> = ArrayList()
    private var singleTypeHolderEnabled = false

    fun changeDataSet(itemList: MutableList<DynamicRecyclerViewHolderBuilder>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    fun clearDataSet() {
        val previousSize = this.itemList.size
        this.itemList.clear()
        notifyItemRangeRemoved(0, previousSize)
    }

    fun insertViewHolderAt(viewHolderItem: DynamicRecyclerViewHolderBuilder, position: Int) {
        itemList.add(position, viewHolderItem)
        notifyItemInserted(position)
    }

    fun appendViewHolder(viewHolderItem: DynamicRecyclerViewHolderBuilder) {
        itemList.add(viewHolderItem)
        notifyItemInserted(itemList.size - 1)
    }

    fun removeViewHolderAt(position: Int) {
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun toggleSingleTypeHolder(enabled: Boolean) {
        singleTypeHolderEnabled = enabled
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val item = itemList[fromPosition]
        itemList.removeAt(fromPosition)
        itemList.add(toPosition, item)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): DynamicRecyclerViewHolder {
        val item = itemList[position]
        return getRecyclerViewHolder(parent, item)
    }

    private fun getRecyclerViewHolder(parent: ViewGroup, item: DynamicRecyclerViewHolderBuilder): DynamicRecyclerViewHolder {
        var recyclerViewHolder: DynamicRecyclerViewHolder? = null
        val layoutInflater = LayoutInflater.from(parent.context)
        val dataBinding: ViewDataBinding?
        if (item.viewHolderClass != null && item.item != null)
            try {
                val layoutName = getLayoutName(item.viewHolderClass)
                val layoutId = context.resources.getIdentifier(layoutName, "layout", context.packageName)
                dataBinding = DataBindingUtil.inflate(layoutInflater, layoutId, parent, false)
                recyclerViewHolder = Class.forName(item.viewHolderClass?.name!!).getConstructor(ViewDataBinding::class.java).newInstance(dataBinding) as DynamicRecyclerViewHolder
            } catch (e: Exception) {
                Log.e(this.javaClass.name, "Cannot find view holder layout pair for " + item.viewHolderClass?.simpleName + ". Create a layout with the same name to accompany the view holder class.")
            }
        else
            try {
                dataBinding = DataBindingUtil.inflate(layoutInflater, item.itemLayoutId, parent, false)
                recyclerViewHolder = DynamicLayoutOnlyRecyclerViewHolder(dataBinding) // instantiate blank holder (layout only)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        return recyclerViewHolder!!
    }

    private fun getLayoutName(viewHolderClass: Class<*>?): String {
        val className = viewHolderClass?.simpleName.toString()
        val classNameModified = className.substring(0, 1).toLowerCase() + className.substring(1)
        val matcher = Pattern.compile("(?<=[a-z])[A-Z]").matcher(classNameModified)
        val buffer = StringBuffer()
        while (matcher.find()) {
            matcher.appendReplacement(buffer, "_" + matcher.group().toLowerCase())
        }
        matcher.appendTail(buffer)
        return buffer.toString()
    }

    override fun onBindViewHolder(holder: DynamicRecyclerViewHolder, position: Int) {
        holder.onBind(context, itemList.get(position).item)
    }

    override fun getItemCount(): Int {
        return if (previewLength == 0 || previewLength > itemList.size) {
            itemList.size
        } else {
            previewLength
        }
    }

    override fun getItemViewType(position: Int): Int =
            if (singleTypeHolderEnabled) super.getItemViewType(position) else position
}