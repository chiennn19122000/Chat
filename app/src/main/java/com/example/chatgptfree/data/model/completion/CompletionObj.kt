package com.example.chatgptfree.data.model.completion

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class CompletionObj(
    @SerializedName("id")
    val id: String,
    @SerializedName("object")
    val `object`: String,
    @SerializedName("created")
    val created: Int,
    @SerializedName("model")
    val model: String,
    @SerializedName("usage")
    val usage: Usage,
    @SerializedName("choices")
    val choices: List<Choices>
)

data class Usage(
    @SerializedName("prompt_tokens")
    val prompt_tokens: Int,
    @SerializedName("completion_tokens")
    val completion_tokens: Int,
    @SerializedName("total_tokens")
    val total_tokens: Int
)

data class Choices(
    @SerializedName("message")
    val message: Message,
    @SerializedName("finish_reason")
    val finish_reason: String,
    @SerializedName("index")
    val index: Int
)

data class Message(
    @SerializedName("role")
    val role: String,
    @SerializedName("content")
    val content: String
)