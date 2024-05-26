package com.example.planet.data.remote.dto.response.chat

data class ChatInfo (
    val chatRoomId: Long,
    val partnerUserId: Long,
    val partnerUserName: String,
    val partnerUserImage: String,
    val content: String,
    val uploadTime: String,
    val newType: Boolean
)