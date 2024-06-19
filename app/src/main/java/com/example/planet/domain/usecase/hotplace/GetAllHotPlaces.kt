package com.example.planet.domain.usecase.hotplace

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.domain.repository.PloggingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllHotPlaces @Inject constructor(private val ploggingRepository: PloggingRepository) {
    suspend operator fun invoke(): Flow<ApiState> {
        return ploggingRepository.getAllHotPlaces()
    }
}