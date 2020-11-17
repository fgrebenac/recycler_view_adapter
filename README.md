Recycler view adapter is an adapter for recycler view using data binding.
You can add different view holders to one recycler view. 
No need to write new adapter for every recycler view, just create view holders. 

# Creating view holder

For creating view holder you need to create layout file with the same name as view holder class. 

```
ViewHolder.kt
class ViewHolder(dataBinding: ViewDataBinding) : DynamicRecyclerViewHolder(dataBinding) {

    private val binding = dataBinding as ViewHolderBinding
    private lateinit var data: HolderData

    data class ViewHolderData(
            val object: Object,
            val viewModel: ViewModel,
            val listener: Listener,
            ...
    )
    
    override fun onBind(context: Context, item: Any?) {
        super.onBind(context, item)
        data = item as ViewHolderData
        binding.holder = this
        // set views
    }
}

view_holder.xml
<layout>
    <data>
        <variable
            name="holder"
            type="com.package.ViewHolder" />
    </data>
    ...
</layout>
```
# In activity/fragment class
```
private fun adapter() = binding.recyclerView.adapter as RecyclerViewAdapter

override fun onCreate() {
    binding.recyclerView.adapter = RecyclerViewAdapter(context)
    ...
    //get data
    ...
    adapter().appendViewHolder(DynamicRecyclerViewHolderBuilder(
                    ViewHolder.ViewHolderData(object, viewModel), 
                    ViewHolder::class.java))
}
```
# In activity/fragment layout file
```
<androidx.recyclerview.widget.RecyclerView
            android:id="@+id/recycler_view"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            // set layout manager and orientation, can be done in code also
            app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
            android:orientation="vertical" />
```
