package com.example.planet.data.remote.dto.response.chat

data class ChatInfo(
    val chatId: Long,
    val content: String,
    val senderImage: String,
    val uploadTime: String,
    val type: String
)
