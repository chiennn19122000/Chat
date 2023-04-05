package com.example.chatgptfree.data.source.remote

import com.example.chatgptfree.data.model.completion.CompletionChat
import com.example.chatgptfree.data.model.completion.CompletionObj
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface Api {

    @Headers("Content-Type:application/json")
    @POST("v1/chat/completions")
    fun getAnswerCompletion(@Body chat: CompletionChat, @Header("Authorization") auth: String): Observable<CompletionObj>
}