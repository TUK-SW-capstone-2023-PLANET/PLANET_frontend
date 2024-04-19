package com.example.planet.domain.usecase.image

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class PostImageUseCase @Inject constructor(private val imageRepository: ImageRepository) {
    suspend operator fun invoke(file: MultipartBody.Part): Flow<ApiState> {
        return imageRepository.getImageUrl(file)
    }
}