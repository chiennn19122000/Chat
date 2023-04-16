package com.example.chatgptfree.data.source.remote

import com.example.chatgptfree.data.model.completion.CompletionChat
import com.example.chatgptfree.data.model.completion.CompletionObj
import com.example.chatgptfree.data.source.CompletionDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class CompletionRemoteDataSource (
    private val api: Api
) : CompletionDataSource {

    override suspend fun getCompletion(chat: CompletionChat, auth: String): CompletionObj = api.getAnswerCompletion(chat, auth)

    override fun textCompletionsTurboWithStream(chat: CompletionChat, auth: String) =
        callbackFlow {
            withContext(Dispatchers.IO) {
                val response = api.textCompletionsTurboWithStream(chat, auth).execute()

                if (response.isSuccessful) {
                    val input = response.body()?.byteStream()?.bufferedReader() ?: throw Exception()
                    try {
                        while (true) {
                            val line = withContext(Dispatchers.IO) {
                                input.readLine()
                            } ?: continue
                            if (line == "data: [DONE]") {
                                close()
                            } else if (line.startsWith("data:")) {
                                try {
                                    // Handle & convert data -> emit to client
                                    // val value = lookupDataFromResponseTurbo(line)

                                    if (line.isNotEmpty()) {
                                        trySend(line)
                                    }
                                } catch (e: Exception) {

                                    e.printStackTrace()
                                }
                            }
                        }
                    } catch (e: IOException) {
                        throw Exception(e)
                    } finally {
                        withContext(Dispatchers.IO) {
                            input.close()
                        }

                        close()
                    }
                } else {
                    if (!response.isSuccessful) {
                        var jsonObject: JSONObject? = null
                        try {
                            jsonObject = JSONObject(response.errorBody()!!.string())
                            println(jsonObject)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    trySend("Failure! Try again.")
                    close()
                }
            }

            close()
        }
}