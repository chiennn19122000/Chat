package com.example.chatgptfree.data.repository

import com.example.chatgptfree.data.model.completion.CompletionChat
import com.example.chatgptfree.data.model.completion.CompletionObj
import kotlinx.coroutines.flow.Flow

interface CompletionRepository {
    suspend fun getCompletion(chat: CompletionChat, auth: String) : CompletionObj

    fun textCompletionsTurboWithStream(chat: CompletionChat, auth: String): Flow<String>
}