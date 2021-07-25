package com.example.kumparan.ui.detailuser

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.kumparan.R
import com.example.kumparan.databinding.DetailUserFragmentBinding
import com.example.kumparan.utils.UiState
import com.example.kumparan.utils.observeNonNull
import com.example.kumparan.utils.showToast
import com.example.kumparan.utils.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailUserFragment : Fragment(R.layout.detail_user_fragment) {

    private val args: DetailUserFragmentArgs by navArgs()

    private val viewModel: DetailUserViewModel by viewModel { parametersOf(args.selectedDetailPostFromUser.userId) }

    private val binding by viewBinding {
        DetailUserFragmentBinding.bind(requireView())
    }

    private val albumAdapter by lazy {
        AlbumAdapter {
            findNavController().navigate(
                DetailUserFragmentDirections.detailUserToDetailPhoto(
                    it.url,
                    it.title
                )
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeData()
    }

    private fun setupView() {
        with(binding) {
            val detailPost = args.selectedDetailPostFromUser
            textName.text = detailPost.name
            textAddress.text =
                "${detailPost.address?.street}, ${detailPost.address?.suite} ,${detailPost.address?.city}, ${detailPost.address?.zipcode}"
            textCompany.text = "Company : ${detailPost.company}"
            textEmail.text = detailPost.email

            recyclerAlbum.adapter = albumAdapter
        }
    }

    private fun observeData() {
        observeNonNull(viewModel.albums) {
            viewModel.getThumbnail(it.map { album -> album.id })
        }

        observeNonNull(viewModel.getAlbumThumbnail()) {
            albumAdapter.items = it
        }

        observeNonNull(viewModel.uiState) {
            binding.progressBar.isVisible = it == UiState.Loading
            if (it is UiState.Error)
                requireContext().showToast(it.message)
        }
    }
}