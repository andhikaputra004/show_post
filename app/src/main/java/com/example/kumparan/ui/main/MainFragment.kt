package com.example.kumparan.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kumparan.R
import com.example.kumparan.databinding.MainFragmentBinding
import com.example.kumparan.utils.UiState
import com.example.kumparan.utils.observeNonNull
import com.example.kumparan.utils.showToast
import com.example.kumparan.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewModel: MainViewModel by viewModel()

    private val postAdapter by lazy {
        PostAdapter {
            findNavController().navigate(MainFragmentDirections.listPostToDetail(it))
        }
    }

    private val binding by viewBinding {
        MainFragmentBinding.bind(requireView())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupView()
        observeData()
    }

    private fun setupView() {
        with(binding) {
            recyclerPost.adapter = postAdapter
        }
    }

    private fun observeData() {
        viewModel.getPost()
        viewModel.getUsers()
        observeNonNull(viewModel.getPostByUser) {
            postAdapter.items = it
        }

        observeNonNull(viewModel.uiState) {
            binding.progressBar.isVisible = it == UiState.Loading
            if (it is UiState.Error)
                requireContext().showToast(it.message)
        }
    }
}