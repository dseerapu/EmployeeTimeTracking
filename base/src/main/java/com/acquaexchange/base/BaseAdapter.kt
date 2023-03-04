package com.acquaexchange.base

import androidx.recyclerview.widget.RecyclerView

/**
 * Base Adapter for all the Adapter in this application
 *
 * @param <ViewHolder>
 * @param <Item>
</Item></ViewHolder> */
abstract class BaseAdapter<Item> : RecyclerView.Adapter<BaseHolder<Item>>() {

    var listItems: ArrayList<Item> = ArrayList()

    open fun submitList(data: List<Item>?) {

        listItems.clear()

        if (data != null) {
            listItems.addAll(ArrayList(data))
        }

        notifyDataSetChanged()
    }

    fun addNewData(newData: List<Item>?) {
        if (newData.isNullOrEmpty()) return

        listItems.addAll(ArrayList(newData))
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(viewHolder: BaseHolder<Item>, position: Int) {

        val item = getItem(position)

        if (item != null) {
            viewHolder.onBind(item)
        }

    }

    override fun onViewRecycled(holder: BaseHolder<Item>) {
        super.onViewRecycled(holder)
        holder.onRecycled()
    }

    open fun getItem(position: Int): Item {
        return listItems[position]
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

}
