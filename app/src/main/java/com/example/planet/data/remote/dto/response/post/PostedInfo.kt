package com.example.planet.data.remote.dto.response.post

data class PostedInfo(
    val postId: Long = 0L,
    val userId: Long = 0L,
    val nickName: String = "",
    val profileUrl: String = "",
    val imageUrl: List<String> = emptyList<String>(),
    val title: String = "",
    val content: String = "",
    val heartCount: Int = 0,
    val commentCount: Int = 0,
    val viewCount: Int = 0,
    val uploadTime: String = "",
    val heart: Boolean = false
)
