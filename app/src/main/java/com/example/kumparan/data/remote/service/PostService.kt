package com.example.kumparan.data.remote.service

import com.example.kumparan.data.remote.model.*
import retrofit2.http.GET
import retrofit2.http.Path

interface PostService {

    @GET("posts")
    suspend fun getAllPost(): List<Post>

    @GET("users")
    suspend fun getAllUsers(): List<User>

    @GET("posts/{postId}/comments")
    suspend fun getCommentByPostId(@Path("postId") postId: Int): List<Comment>

    @GET("users/{userId}/albums")
    suspend fun getAlbumByUserId(@Path("userId") userId: Int): List<Album>

    @GET("albums/{albumId}/photos")
    suspend fun getPhotoByAlbumId(@Path("albumId") albumId: Int): List<Photo>

}