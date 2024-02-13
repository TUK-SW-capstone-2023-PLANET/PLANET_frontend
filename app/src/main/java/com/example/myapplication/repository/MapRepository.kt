package com.example.myapplication.repository

import com.example.myapplication.data.ApiState
import com.example.myapplication.network.GeocoderApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MapRepository @Inject constructor(private val geocoderApi: GeocoderApi) {
    suspend fun getRegion(coords: String): Flow<ApiState> = flow {
        kotlin.runCatching {
            geocoderApi.getRegion(coords = coords)
        }.onSuccess {
            emit(ApiState.Success(it))
        }.onFailure { error ->
            error.message?.let { emit(ApiState.Error(it)) }
        }
    }.flowOn(Dispatchers.IO)

}