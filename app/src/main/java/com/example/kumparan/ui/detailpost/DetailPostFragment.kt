package com.example.kumparan.ui.detailpost

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.kumparan.R
import com.example.kumparan.data.remote.model.DetailPostDTO
import com.example.kumparan.databinding.DetailPostFragmentBinding
import com.example.kumparan.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailPostFragment : Fragment(R.layout.detail_post_fragment) {

    private val args: DetailPostFragmentArgs by navArgs()

    private val viewModel: DetailPostViewModel by viewModel { parametersOf(args.selectedPost) }

    private val binding by viewBinding {
        DetailPostFragmentBinding.bind(requireView())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun setupView(detailPost: DetailPostDTO) {
        with(binding) {
            textTitle.text = detailPost.title
            textName.text = detailPost.name
            textBody.text = detailPost.body

            textName.underline()

            textName.setOnClickListener {
                findNavController().navigate(
                    DetailPostFragmentDirections.detailPostToDetailUser(
                        detailPost
                    )
                )
            }

            val commentAdapter = CommentAdapter()

            binding.recyclerComment.adapter = commentAdapter

            commentAdapter.items = detailPost.comments

        }
    }

    private fun observeData() {
        observeNonNull(viewModel.detailComment) {
            setupView(it)
        }

        observeNonNull(viewModel.uiState) {
            binding.progressBar.isVisible = it == UiState.Loading
            if (it is UiState.Error)
                requireContext().showToast(it.message)
        }
    }

}