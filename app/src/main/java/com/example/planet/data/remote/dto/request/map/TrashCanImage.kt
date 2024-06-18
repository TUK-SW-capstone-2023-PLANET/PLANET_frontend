package com.example.planet.data.remote.dto.request.map

import com.example.planet.data.remote.dto.Location

data class TrashCanImage(
    val userId: Long,
    val latitude: Double,
    val longitude: Double,
    val imageUrl: String
)