package com.example.planet.data.remote.dto.request.post

data class CommentInfo(
    val postId: Long,
    val userId: Long,
    val content: String
)
