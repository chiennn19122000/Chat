package com.example.chatgptfree.data.model.message

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MessCompletion(
    val mess: String,
    val sentByMe: Boolean
): Parcelable {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MessCompletion>() {
            override fun areItemsTheSame(oldItem: MessCompletion, newItem: MessCompletion) =
                oldItem.mess == newItem.mess

            override fun areContentsTheSame(oldItem: MessCompletion, newItem: MessCompletion) = oldItem == newItem
        }
    }
}
