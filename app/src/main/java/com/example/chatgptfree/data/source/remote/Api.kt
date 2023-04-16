package com.example.chatgptfree.data.source.remote

import com.example.chatgptfree.data.model.completion.CompletionChat
import com.example.chatgptfree.data.model.completion.CompletionObj
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Streaming

interface Api {

    @Headers("Content-Type:application/json")
    @POST("v1/chat/completions")
    @Streaming
    suspend fun getAnswerCompletion(@Body chat: CompletionChat, @Header("Authorization") auth: String): CompletionObj

    @Headers("Content-Type:application/json")
    @POST("v1/chat/completions")
    @Streaming
    fun textCompletionsTurboWithStream(@Body chat: CompletionChat, @Header("Authorization") auth: String): Call<ResponseBody>
}