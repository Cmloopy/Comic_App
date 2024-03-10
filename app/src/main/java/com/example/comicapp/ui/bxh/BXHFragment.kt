package com.example.comicapp.ui.bxh

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.comicapp.databinding.FragmentBxhBinding


class BXHFragment : Fragment() {

    private var _binding: FragmentBxhBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val calendarViewModel =
            ViewModelProvider(this)[BXHViewModel::class.java]

        _binding = FragmentBxhBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textBxh
        calendarViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}