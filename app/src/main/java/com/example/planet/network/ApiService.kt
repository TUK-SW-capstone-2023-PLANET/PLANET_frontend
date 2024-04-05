package com.example.planet.network

import com.example.planet.data.dto.Advertisement
import com.example.planet.data.dto.ImageUrl
import com.example.planet.data.dto.PloggingComplete
import com.example.planet.data.dto.PloggingImage
import com.example.planet.data.dto.PloggingInfo
import com.example.planet.data.dto.SeasonPerson
import com.example.planet.data.dto.Tier
import com.example.planet.data.dto.TrashCan
import com.example.planet.data.dto.ranking.University
import com.example.planet.data.dto.ranking.UniversityPerson
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
    suspend fun getTopBanner(): List<Advertisement>

    // 대학교 랭킹 3개 조회
    @GET("/university/rank")
    suspend fun getHigherUniversities(): List<University>
// 대학교 유저 랭킹 전체 조회
    @GET("/user/{userId}/rank/university/all")
    suspend fun getUniversityAllUserInfo(@Path("userId") userId: Int): List<Map<Int, UniversityPerson>>
    // 대학교 유저 랭킹 4개 조회 - 상단 조회
    @GET("/user/{userId}/rank/university/4")
    suspend fun getUniversityPartUserInfo(@Path("userId") userId: Int): List<Map<Int, UniversityPerson>>

    @GET("/season/user/{userId}")
    suspend fun getSeasonInfo(@Path("userId") userId: Int): List<Map<Int, SeasonPerson>>

    @GET("/tier")
    suspend fun getTierList(): List<Tier>

}