package com.example.chatgptfree.data.repository

import com.example.chatgptfree.data.model.completion.CompletionChat
import com.example.chatgptfree.data.model.completion.CompletionObj
import com.example.chatgptfree.data.source.CompletionDataSource
class CompletionRepositoryImpl(
    private val remote: CompletionDataSource
): CompletionRepository {
    override suspend fun getCompletion(chat: CompletionChat, auth: String): CompletionObj = remote.getCompletion(chat, auth)
}