package com.example.chatgptfree.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(
    binding: ViewDataBinding,
    itemClick: (T) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var item: T? = null

    init {
        binding.root.setOnClickListener {
            item?.let {
                itemClick(it)
            }
        }
    }

    open fun bindData(item: T) {
        this.item = item
    }
}
