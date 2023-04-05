package com.example.chatgptfree.data.source

import com.example.chatgptfree.data.model.completion.CompletionChat
import com.example.chatgptfree.data.model.completion.CompletionObj
import io.reactivex.rxjava3.core.Observable

interface CompletionDataSource {
    fun getCompletion(chat: CompletionChat, auth: String): Observable<CompletionObj>
}