package com.example.kumparan.ui.main

import androidx.lifecycle.*
import com.example.kumparan.data.mapper.toDTO
import com.example.kumparan.data.remote.model.Post
import com.example.kumparan.data.remote.model.PostDTO
import com.example.kumparan.data.remote.model.User
import com.example.kumparan.domain.PostRepository
import com.example.kumparan.utils.ActionLiveData
import com.example.kumparan.utils.UiState
import kotlinx.coroutines.launch

class MainViewModel(private val repository: PostRepository) : ViewModel() {

    private val post = MutableLiveData<List<Post>>()

    private val users = MutableLiveData<List<User>>()

    val uiState = ActionLiveData<UiState>()

    val getPostByUser = post.switchMap { postData ->
        users.map { userData ->
            postData.map { post ->

                val user = userData.find { it.id == post.userId }
                PostDTO(
                    post.title,
                    post.body,
                    user?.name,
                    user?.company?.name,
                    user?.email,
                    post.userId,
                    user?.address?.toDTO(),
                    user?.company?.toDTO()
                )
            }
        }
    }

    fun getPost() {
        viewModelScope.launch {
            try {
                uiState.sendAction(UiState.Loading)
                post.postValue(repository.getAllPostUsers())
                uiState.sendAction(UiState.Success)
            } catch (error: Exception) {
                uiState.sendAction(UiState.Error(error.message ?: "Something Went Wrong"))
            }
        }
    }

    fun getUsers() {
        viewModelScope.launch {
            try {
                uiState.sendAction(UiState.Loading)
                users.postValue(repository.getAllUsers())
                uiState.sendAction(UiState.Success)
            } catch (error: Exception) {
                uiState.sendAction(UiState.Error(error.message ?: "Something Went Wrong"))
            }
        }
    }
}