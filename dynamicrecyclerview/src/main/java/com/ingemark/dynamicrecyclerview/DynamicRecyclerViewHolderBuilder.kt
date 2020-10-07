package com.ingemark.dynamicrecyclerview


class DynamicRecyclerViewHolderBuilder {

    internal var item: Any? = null
    internal var viewHolderClass: Class<*>? = null

    internal var itemLayoutId: Int = 0

    constructor(itemLayoutId: Int) {
        this.itemLayoutId = itemLayoutId
    }

    constructor(item: Any?, viewHolderClass: Class<*>) {
        this.item = item
        this.viewHolderClass = viewHolderClass
    }
}