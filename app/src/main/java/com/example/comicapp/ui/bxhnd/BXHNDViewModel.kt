package com.example.comicapp.ui.bxhnd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BXHNDViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is bxhnd Fragment"
    }
    val text: LiveData<String> = _text
}