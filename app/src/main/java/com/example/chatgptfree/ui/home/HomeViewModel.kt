package com.example.chatgptfree.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chatgptfree.base.BaseViewModel
import com.example.chatgptfree.data.model.completion.CompletionChat
import com.example.chatgptfree.data.model.completion.CompletionObj
import com.example.chatgptfree.data.model.completion.Message
import com.example.chatgptfree.data.model.message.MessCompletion
import com.example.chatgptfree.data.repository.CompletionRepository
import com.example.chatgptfree.ui.adapter.MessageAdapter
import com.example.chatgptfree.utils.IntegerCallback
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val completionRepository: CompletionRepository
) : BaseViewModel() {

    private val _completion = MutableLiveData<CompletionObj>()
    val completion: LiveData<CompletionObj>
        get() = _completion

    private val _messCompletion = MutableLiveData<MutableList<MessCompletion>>()
    val messCompletion: LiveData<MutableList<MessCompletion>>
        get() = _messCompletion

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isShow = MutableLiveData<Boolean>()
    val isShow: LiveData<Boolean>
        get() = _isShow

    private val _text = MutableLiveData<String>()
    val text: LiveData<String>
        get() = _text

    private val listMess: MutableList<Message> by lazy { mutableListOf() }

    init {
        _messCompletion.value = mutableListOf()
        _isLoading.value = false
        _isShow.value = false
    }

    suspend fun getCompletion(
        message: Message,
        auth: String,
        adapter: MessageAdapter,
        callback: IntegerCallback
    ) {
        try {
            withContext(Dispatchers.Main) {
                _isLoading.value = true
            }

            if (listMess.size <= 10) listMess.add(message)
            else listMess.apply {
                this.removeFirst()
                this.add(message)
            }

            val content = CompletionChat("gpt-3.5-turbo", listMess, 0.5)
            //  val result = completionRepository.getCompletion(content, auth)

            val flow: Flow<String> =
                completionRepository.textCompletionsTurboWithStream(content, auth)

            var answerFromGPT = ""

            val mess = Message("assistant", "")
            if (listMess.size <= 10) listMess.add(mess)
            else listMess.apply {
                this.removeFirst()
                this.add(mess)
            }
            var checkAddAnswer = false

            flow.collect {
                answerFromGPT = it.replace("delta", "message").replace("data: ", "")
                val completionObj = Gson().fromJson(answerFromGPT, CompletionObj::class.java)
                mess.content += completionObj.choices[0].message.content ?: ""

                withContext(Dispatchers.Main) {
                    _isShow.value = true
                }
//                if (!checkAddAnswer) {
//                    _messCompletion.value?.add(MessCompletion(mess.content ?: "", false))
//                    checkAddAnswer = true
//                } else {
//                    _messCompletion.value?.apply {
//                        set(lastIndex, MessCompletion(mess.content ?: "", false))
//                    }
//                }

                withContext(Dispatchers.Main) {
                    _text.value = mess.content?:""
                }

            }
            withContext(Dispatchers.Main) {
                _isLoading.value = false
                _isShow.value = false
                _text.value = ""
                _messCompletion.value?.add(MessCompletion(mess.content ?: "", false))
                changeAdapter(adapter, callback)

            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                _isLoading.value = false
                _isShow.value = false
                _error.value = e.message.toString()
                _messCompletion.value?.apply {
                    set(lastIndex, MessCompletion(_error.value.toString(), false))
                }
                changeAdapter(adapter, callback)
            }
        }
    }

    fun setContentToMe(
        message: MessCompletion,
        adapter: MessageAdapter,
        callback: IntegerCallback
    ) {
        _messCompletion.value?.add(message)
        changeAdapter(adapter, callback)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun changeAdapter(adapter: MessageAdapter, callback: IntegerCallback) {
        if ((_messCompletion.value?.size ?: 0) > 0)
            adapter.notifyItemChanged(_messCompletion.value?.lastIndex ?: 0)
        else
            adapter.notifyDataSetChanged()

        callback.execute(_messCompletion.value?.size ?: 0)
    }

}