package com.example.planet.data.remote.api.ai

import com.example.planet.data.remote.dto.response.plogging.TrashImage
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AiApi {
    @Multipart
    @POST("/")
    suspend fun postImage(@Part file: MultipartBody.Part): TrashImage
}