package com.example.chatgptfree.data.repository

import com.example.chatgptfree.data.model.completion.CompletionChat
import com.example.chatgptfree.data.model.completion.CompletionObj
import io.reactivex.rxjava3.core.Observable

interface CompletionRepository {
    fun getCompletion(chat: CompletionChat, auth: String) : Observable<CompletionObj>
}