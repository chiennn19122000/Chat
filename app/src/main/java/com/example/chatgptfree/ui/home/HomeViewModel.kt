package com.example.chatgptfree.ui.home

import android.annotation.SuppressLint
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
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

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

    private val listMess: MutableList<Message> by lazy { mutableListOf() }

    init {
        _messCompletion.value = mutableListOf()
        _isLoading.value = false
    }

    fun getCompletion(message: Message, auth: String, adapter: MessageAdapter, callback: IntegerCallback) {
        _isLoading.value = true
        if (listMess.size <= 10) listMess.add(message)
        else listMess.apply {
            this.removeFirst()
            this.add(message)
        }
        val content = CompletionChat("gpt-3.5-turbo", listMess,0.5)
        completionRepository.getCompletion(content, auth)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _isLoading.value = false
                _completion.value = it
                if (listMess.size <= 10) listMess.add(it.choices[0].message)
                else listMess.apply {
                    this.removeFirst()
                    this.add(it.choices[0].message)
                }
                _messCompletion.value?.add(MessCompletion(it.choices[0].message.content, false))
                changeAdapter(adapter, callback)

            }, {
                _isLoading.value = false
                _error.value = it.message.toString()
                _messCompletion.value?.add(MessCompletion(_error.value.toString(), false))
                changeAdapter(adapter, callback)
            })
            .addTo(disposable)
    }

    fun setContentToMe(message: MessCompletion, adapter: MessageAdapter, callback: IntegerCallback) {
        _messCompletion.value?.add(message)
        changeAdapter(adapter,callback)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun changeAdapter(adapter: MessageAdapter, callback: IntegerCallback){
        if ((_messCompletion.value?.size ?: 0) > 0)
            adapter.notifyItemInserted(_messCompletion.value?.size!!)
        else
            adapter.notifyDataSetChanged()

        callback.execute(_messCompletion.value?.size ?: 0)
    }

}