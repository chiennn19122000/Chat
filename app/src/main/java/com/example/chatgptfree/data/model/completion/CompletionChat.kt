package com.example.chatgptfree.data.model.completion

import com.google.gson.annotations.SerializedName

data class CompletionChat(
    @SerializedName("model")
    val model: String ,
    @SerializedName("messages")
    val messages: List<Message> ,
    @SerializedName("temperature")
    val temperature: Double
)
