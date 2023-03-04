package com.acquaexchange.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseHolder<Item>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun onBind(item: Item)

    open fun onRecycled() {
        // override
    }

}
