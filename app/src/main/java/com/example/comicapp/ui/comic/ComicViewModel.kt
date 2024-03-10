package com.example.comicapp.ui.comic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ComicViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is comic Fragment"
    }
    val text: LiveData<String> = _text
}