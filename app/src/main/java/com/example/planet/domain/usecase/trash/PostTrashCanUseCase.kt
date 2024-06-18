package com.example.planet.domain.usecase.trash

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.map.TrashCanImage
import com.example.planet.domain.repository.PloggingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostTrashCanUseCase  @Inject constructor(private val ploggingRepository: PloggingRepository) {
    suspend operator fun invoke(trashCanImage: TrashCanImage): Flow<ApiState> {
        return ploggingRepository.saveTrashCan(trashCanImage)
    }
}