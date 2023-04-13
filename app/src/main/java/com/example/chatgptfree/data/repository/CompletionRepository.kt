package com.example.chatgptfree.data.repository

import com.example.chatgptfree.data.model.completion.CompletionChat
import com.example.chatgptfree.data.model.completion.CompletionObj

interface CompletionRepository {
    suspend fun getCompletion(chat: CompletionChat, auth: String) : CompletionObj
}