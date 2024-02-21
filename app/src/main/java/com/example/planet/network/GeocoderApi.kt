package com.example.planet.network

import com.example.planet.data.Geocoding
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocoderApi {
    @GET("/map-reversegeocode/v2/gc")
    // reverseGeocoder
    suspend fun getRegion(
        @Query("coords") coords: String
    ): Geocoding
}