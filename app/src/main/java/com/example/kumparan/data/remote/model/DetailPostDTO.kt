package com.example.kumparan.data.remote.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DetailPostDTO(
    val title: String,
    val body: String,
    val name: String?,
    val email : String?,
    val address : AddressDTO?,
    val company: String?,
    val userId : Int,
    val comments: List<CommentDTO>
) : Parcelable

@Parcelize
data class CommentDTO(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
) : Parcelable