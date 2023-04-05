package com.example.chatgptfree.data.repository

import com.example.chatgptfree.data.model.completion.CompletionChat
import com.example.chatgptfree.data.model.completion.CompletionObj
import com.example.chatgptfree.data.source.CompletionDataSource
import io.reactivex.rxjava3.core.Observable

class CompletionRepositoryImpl(
    private val remote: CompletionDataSource
): CompletionRepository {
    override fun getCompletion(chat: CompletionChat, auth: String): Observable<CompletionObj> = remote.getCompletion(chat, auth)
}