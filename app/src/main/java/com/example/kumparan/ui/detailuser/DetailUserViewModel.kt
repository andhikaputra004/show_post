package com.example.kumparan.ui.detailuser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kumparan.data.remote.model.Album
import com.example.kumparan.data.remote.model.AlbumDTO
import com.example.kumparan.data.remote.model.Photo
import com.example.kumparan.data.remote.model.PhotoDTO
import com.example.kumparan.domain.PostRepository
import com.example.kumparan.utils.ActionLiveData
import com.example.kumparan.utils.UiState
import com.snakydesign.livedataextensions.map
import com.snakydesign.livedataextensions.switchMap
import kotlinx.coroutines.launch

class DetailUserViewModel(private val repository: PostRepository, userId: Int) :
    ViewModel() {

    val albums = MutableLiveData<List<Album>>()

    val uiState = ActionLiveData<UiState>()

    val photos = MutableLiveData<List<Photo>>()

    private val photoData = mutableListOf<Photo>()

    private fun getAlbumByUserId(userId: Int) {
        viewModelScope.launch {
            try {
                uiState.sendAction(UiState.Loading)
                albums.postValue(repository.getAlbumByUserId(userId))
                uiState.sendAction(UiState.Success)
            } catch (error: Exception) {
                uiState.sendAction(UiState.Error(error.message ?: "Something Went Wrong"))
            }
        }
    }

    fun getThumbnail(albumIds: List<Int>) {
        viewModelScope.launch {
            try {
                uiState.sendAction(UiState.Loading)
                albumIds.forEach {
                    val response = repository.getPhotoByAlbumId(it)
                    photoData.addAll(response)
                }
                photos.postValue(photoData.toList())
                uiState.sendAction(UiState.Success)
            } catch (error: Exception) {
                uiState.sendAction(UiState.Error(error.message ?: "Something Went Wrong"))
            }
        }
    }

    fun getAlbumThumbnail() = albums.switchMap { albumsData ->
        photos.map { photosData ->
            albumsData.map { album ->
                AlbumDTO(
                    album.id,
                    album.title,
                    album.userId,
                    photosData.filter {
                        it.albumId == album.id
                    }.map {
                        PhotoDTO(
                            it.albumId,
                            it.id,
                            it.thumbnailUrl,
                            it.title,
                            it.url
                        )
                    })
            }
        }
    }

    init {
        getAlbumByUserId(userId)
    }
}