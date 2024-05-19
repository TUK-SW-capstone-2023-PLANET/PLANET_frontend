package com.example.planet.data.remote.dto.response.post

data class Posted (
    val postId: Long = 0L,
    val userId: Long = 0L,
    val nickName: String = "",
    val title: String = "",
    val content: String = "",
    val heartCount: Int = 0,
    val commentCount: Int = 0,
    val viewCount: Int = 0,
    val imageCount: Int = 0,
    val uploadTime: String = "",
)