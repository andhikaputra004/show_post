package com.example.kumparan.data.remote.model

data class AlbumDTO(
    val id: Int,
    val title: String,
    val userId: Int,
    val listPhoto: List<PhotoDTO>,
)

data class PhotoDTO(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
)