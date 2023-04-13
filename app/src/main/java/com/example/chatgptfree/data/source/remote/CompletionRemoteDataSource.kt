package com.example.chatgptfree.data.source.remote

import com.example.chatgptfree.data.model.completion.CompletionChat
import com.example.chatgptfree.data.model.completion.CompletionObj
import com.example.chatgptfree.data.source.CompletionDataSource

class CompletionRemoteDataSource (
    private val api: Api
) : CompletionDataSource {

    override suspend fun getCompletion(chat: CompletionChat, auth: String): CompletionObj = api.getAnswerCompletion(chat, auth)
}