package com.example.kumparan.ui.detailphoto

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.kumparan.R
import com.example.kumparan.databinding.DetailPhotoFragmentBinding
import com.example.kumparan.utils.viewBinding


class DetailPhotoFragment : Fragment(R.layout.detail_photo_fragment) {

    private val args: DetailPhotoFragmentArgs by navArgs()

    private val binding by viewBinding {
        DetailPhotoFragmentBinding.bind(requireView())
    }

    private val mScaleGestureDetector by lazy {
        ScaleGestureDetector(requireContext(), ScaleListener())
    }

    private var scaleFactor = 1.0f

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupView() {
        with(binding) {
            textPhotoName.text = args.title
            photoAlbum.load(args.urlPhoto)
            photoAlbum.setOnTouchListener { v, event ->
                mScaleGestureDetector.onTouchEvent(event)
                true
            }
        }
    }

    inner class ScaleListener : SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            scaleFactor *= scaleGestureDetector.scaleFactor
            binding.photoAlbum.scaleX = scaleFactor;
            binding.photoAlbum.scaleY = scaleFactor;
            return true
        }
    }
}

