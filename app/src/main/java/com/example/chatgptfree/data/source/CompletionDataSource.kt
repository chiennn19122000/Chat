package com.example.chatgptfree.data.source

import com.example.chatgptfree.data.model.completion.CompletionChat
import com.example.chatgptfree.data.model.completion.CompletionObj

interface CompletionDataSource {
    suspend fun getCompletion(chat: CompletionChat, auth: String): CompletionObj
}