package com.example.planet.network

import com.example.planet.data.dto.TrashCan
import retrofit2.http.GET

interface ApiService {
    @GET("/trash-can/all")
    suspend fun getAllTrashCanLocation(): List<TrashCan>
}