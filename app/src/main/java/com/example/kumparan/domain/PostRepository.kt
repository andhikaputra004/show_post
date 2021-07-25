package com.example.kumparan.domain

import com.example.kumparan.data.remote.model.*
import com.example.kumparan.data.remote.service.PostService

class PostRepository(private val service: PostService) {

    suspend fun getAllPostUsers(): List<Post> {
        return service.getAllPost()
    }

    suspend fun getAllUsers(): List<User> {
        return service.getAllUsers()
    }

    suspend fun getCommentByPostId(postId: Int): List<Comment> {
        return service.getCommentByPostId(postId)
    }

    suspend fun getAlbumByUserId(userId: Int): List<Album> {
        return service.getAlbumByUserId(userId)
    }

    suspend fun getPhotoByAlbumId(albumId: Int): List<Photo> {
        return service.getPhotoByAlbumId(albumId)
    }
}