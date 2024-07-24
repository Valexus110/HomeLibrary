package com.valexus.homelibrary.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.valexus.homelibrary.databinding.FragmentAllBookBinding
import com.valexus.homelibrary.ui.main.adapters.BookItemAdapter

class AllBookFragment : Fragment() {
    companion object {

    }

    private var _binding: FragmentAllBookBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllBookBinding.inflate(inflater, container, false)
        val adapter = BookItemAdapter()
        binding.recyclerBookList.adapter = adapter
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}