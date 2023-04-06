package com.example.chatgptfree.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.chatgptfree.R
import com.example.chatgptfree.base.BaseAdapter
import com.example.chatgptfree.base.BaseViewHolder
import com.example.chatgptfree.data.model.message.MessCompletion
import com.example.chatgptfree.databinding.ItemMessBinding

class MessageAdapter(
private val itemClick: (MessCompletion) -> Unit
) : BaseAdapter<MessCompletion, MessageAdapter.MessViewHolder>(MessCompletion.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessViewHolder =
        MessViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_mess, parent, false
            ),
            itemClick
        )

    class MessViewHolder(
        private val itemMessBinding: ItemMessBinding,
        itemClick: (MessCompletion) -> Unit
    ) : BaseViewHolder<MessCompletion>(itemMessBinding, itemClick) {

        override fun bindData(item: MessCompletion) {
            super.bindData(item)
            itemMessBinding.mess = item
        }
    }
}