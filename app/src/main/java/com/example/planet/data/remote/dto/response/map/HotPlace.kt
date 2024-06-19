package com.example.planet.data.remote.dto.response.map

import com.naver.maps.geometry.LatLng

data class HotPlace(
    val location: LatLng,
    val address: String,
    val ploggingCount: Int
)
