package com.example.comicapp.ui.bxh

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BXHViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is BXH Fragment"
    }
    val text: LiveData<String> = _text
}