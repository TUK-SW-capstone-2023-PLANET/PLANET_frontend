package com.example.planet.data.remote.dto.request.post

data class CommentInfo(
    val postId: String,
    val userId: String,
    val content: String
)
