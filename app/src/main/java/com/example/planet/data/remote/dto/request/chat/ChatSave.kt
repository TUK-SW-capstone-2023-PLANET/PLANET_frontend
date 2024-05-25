package com.example.planet.data.remote.dto.request.chat

data class ChatSave(
    val senderId: Long,
    val receiverId: Long,
    val content: String
)
