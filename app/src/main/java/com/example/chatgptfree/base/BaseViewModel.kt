package com.example.chatgptfree.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    protected val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

}
