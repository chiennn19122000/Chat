package com.example.chatgptfree.data.source.remote

import com.example.chatgptfree.data.model.completion.CompletionChat
import com.example.chatgptfree.data.model.completion.CompletionObj
import com.example.chatgptfree.data.source.CompletionDataSource
import io.reactivex.rxjava3.core.Observable

class CompletionRemoteDataSource (
    private val api: Api
) : CompletionDataSource {

    override fun getCompletion(chat: CompletionChat, auth: String): Observable<CompletionObj> = api.getAnswerCompletion(chat, auth)
}