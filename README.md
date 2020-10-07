# USAGE

{class ViewHolder(dataBinding: ViewDataBinding) : DynamicRecyclerViewHolder(dataBinding) {

    private val binding = dataBinding as ViewHolderBinding
    private lateinit var data: HolderData

    data class ViewHolderData(
            val object: Object,
            val viewModel: ViewModel,
    )
    
    override fun onBind(context: Context, item: Any?) {
        super.onBind(context, item)
        data = item as FavoriteViewHolderData
        binding.holder = this
    }
}}

{adapter().appendViewHolder(DynamicRecyclerViewHolderBuilder(
                    ViewHolder.ViewHolderData(
                            object, viewModel), ViewHolder::class.java))}
