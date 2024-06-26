package com.example.planet.domain.usecase.trash

import com.example.planet.data.remote.dto.ApiState
import com.example.planet.data.remote.dto.request.plogging.TrashImageUrlInfo
import com.example.planet.domain.repository.PloggingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostTrashImageUrlUseCase @Inject constructor(private val ploggingRepository: PloggingRepository) {
    suspend operator fun invoke(trashImageUrlInfo: TrashImageUrlInfo): Flow<ApiState> {
        return ploggingRepository.postPloggingLive(trashImageUrlInfo)
    }
}