package com.example.myapplication.network

import com.example.myapplication.data.NaverRegion
import com.naver.maps.geometry.LatLng
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GeocoderApi {
    @GET("/map-reversegeocode/v2/gc")
    // reverseGeocoder
    suspend fun getMonthWaterBill(
        @Query("coords") coords: LatLng
    ): NaverRegion
}