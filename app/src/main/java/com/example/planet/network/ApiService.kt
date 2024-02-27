package com.example.planet.network

import com.example.planet.data.dto.ImageUrl
import com.example.planet.data.dto.PloggingImage
import com.example.planet.data.dto.Trash
import com.example.planet.data.dto.TrashCan
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @GET("/trash-can/all")
    suspend fun getAllTrashCanLocation(): List<TrashCan>

    @GET("/plogging/id")
    suspend fun getPloggingId(): Int

    @Multipart
    @POST("/image")
    suspend fun postImage(@Part file: MultipartBody.Part): ImageUrl

    @POST("/plogging-live")
    suspend fun postPloggingLive(@Body ploggingImage: PloggingImage): List<Trash>

}