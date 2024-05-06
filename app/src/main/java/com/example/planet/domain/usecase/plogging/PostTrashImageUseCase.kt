package com.example.planet.domain.usecase.plogging

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.PloggingRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class PostTrashImageUseCase  @Inject constructor(private val ploggingRepository: PloggingRepository) {
    suspend operator fun invoke(file: MultipartBody.Part): Flow<ApiState> {
        return ploggingRepository.postTrashImages(file)
    }
}