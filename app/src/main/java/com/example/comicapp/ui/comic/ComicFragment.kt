package com.example.comicapp.ui.comic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.comicapp.databinding.ActivityMainBinding
import com.example.comicapp.databinding.FragmentComicBinding
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class ComicFragment : Fragment() {

    private lateinit var binding: FragmentComicBinding
    private val storageReference = FirebaseStorage.getInstance().reference.child("nguyen_ton/chapter_1/001.jpg")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentComicBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load ảnh từ Firebase Storage
        lifecycleScope.launch {
            try {
                val downloadUrl = withContext(Dispatchers.IO) {
                    storageReference.downloadUrl.await()
                }
                Glide.with(requireContext()).load(downloadUrl).into(binding.imageView)
            } catch (e: Exception) {
                // Xử lý khi không thể tải ảnh
            }
        }
    }
}