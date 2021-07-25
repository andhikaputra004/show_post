package com.example.kumparan.ui.detailpost

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.kumparan.data.mapper.toDTO
import com.example.kumparan.data.remote.model.Comment
import com.example.kumparan.data.remote.model.DetailPostDTO
import com.example.kumparan.data.remote.model.PostDTO
import com.example.kumparan.domain.PostRepository
import com.example.kumparan.utils.ActionLiveData
import com.example.kumparan.utils.UiState
import kotlinx.coroutines.launch

class DetailPostViewModel(
    private val repository: PostRepository,
    private val selectedPost: PostDTO
) : ViewModel() {

    private val comment = MutableLiveData<List<Comment>>()

    val uiState = ActionLiveData<UiState>()

    val detailComment = comment.map {
        DetailPostDTO(
            selectedPost.title,
            selectedPost.body,
            selectedPost.name,
            selectedPost.email,
            selectedPost.userAddressDTO,
            selectedPost.company,
            selectedPost.userId,
            it.map { comment -> comment.toDTO() })
    }

    private fun getCommentByPostId(postId: Int) {
        viewModelScope.launch {
            try {
                uiState.sendAction(UiState.Loading)
                comment.postValue(repository.getCommentByPostId(postId))
                uiState.sendAction(UiState.Success)
            } catch (error: Exception) {
                uiState.sendAction(UiState.Error(error.message ?: "Something Went Wrong"))
            }
        }
    }

    init {
        getCommentByPostId(selectedPost.userId)
    }
}