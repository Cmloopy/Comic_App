package com.example.comicapp.ui.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PersonViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is my Fragment"
    }
    val text: LiveData<String> = _text
}