package com.example.kumparan.data.mapper

import com.example.kumparan.data.remote.model.Comment
import com.example.kumparan.data.remote.model.CommentDTO

fun Comment.toDTO(): CommentDTO {
    return CommentDTO(body, email, id, name, postId)
}