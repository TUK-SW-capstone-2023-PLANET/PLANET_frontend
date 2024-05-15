package com.example.planet.data.remote.dto.request.post

import com.example.planet.data.remote.dto.response.signup.UserId

data class PostingInfo(
    val userId: Long,
    val imageUrl: List<String>,
    val title: String,
    val content: String
)