package com.example.planet.data.remote.dto.request.post

data class CommentRequest(
    val postId: Long,
    val userId: Long,
    val content: String
)
