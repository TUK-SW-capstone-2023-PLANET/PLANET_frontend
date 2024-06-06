package com.example.planet.data.remote.dto.response.map

import com.google.android.gms.maps.model.LatLng

data class SearchPlace(
    val text: String,
    val addressCheck: Boolean,
    val date: String,
    val location: LatLng
)