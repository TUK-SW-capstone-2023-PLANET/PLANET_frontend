package com.example.planet.data.remote.dto.response.post

data class CommentInfo (
    val commentId: Long,
    val userId: Long,
    val nickName: String,
    val imageUrl: String,
    val content: String,
    val heartCount: Int,
    val heart: Boolean,
    val uploadTime: String
)