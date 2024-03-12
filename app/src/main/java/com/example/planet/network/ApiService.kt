package com.example.planet.network

import com.example.planet.data.dto.Advertisement
import com.example.planet.data.dto.ImageUrl
import com.example.planet.data.dto.PloggingComplete
import com.example.planet.data.dto.PloggingImage
import com.example.planet.data.dto.PloggingInfo
import com.example.planet.data.dto.SeasonPeople
import com.example.planet.data.dto.TrashCan
import com.example.planet.data.dto.UniversityPerson
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @GET("/trash-can/all")
    suspend fun getAllTrashCanLocation(): List<TrashCan>

    @GET("/plogging/id")
    suspend fun getPloggingId(): Int

    @Multipart
    @POST("/image")
    suspend fun postImage(@Part file: MultipartBody.Part): ImageUrl

    @POST("/plogging-live")
    suspend fun postPloggingLive(@Body ploggingImage: PloggingImage): List<Map<String, Int>>

    @POST("/plogging")
    suspend fun postPlogging(@Body ploggingInfo: PloggingInfo): PloggingComplete

    @GET("/advertisement")
    suspend fun getAdvertisement(): List<Advertisement>

    @GET("/user/{userId}/university")
    suspend fun getUniversityPeople(@Path("userId") userId: Int): List<Map<Int, UniversityPerson>>

    @GET("/season/user/{userId}")
    suspend fun getSeasonPeople(@Path("userId") userId: Int): List<Map<Int, SeasonPeople>>

}