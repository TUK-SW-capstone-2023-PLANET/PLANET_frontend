package com.example.planet.data.repository.paging

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.planet.TAG
import com.example.planet.data.remote.api.ApiService
import com.example.planet.data.remote.dto.response.ranking.planet.PlanetRankingUser
import java.io.IOException
import javax.inject.Inject

class PlanetPagingSource @Inject constructor(
    private val apiService: ApiService
): PagingSource<Int, PlanetRankingUser>() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlanetRankingUser> {
        return try {
            Log.d(TAG, "PlanetPagingSource() 실행")
            val currentPage = params.key ?: 0
            val users = apiService.getAllPlanetUserRanking(
                page = currentPage
            )
            LoadResult.Page(
                data = users.content,
                prevKey = if (currentPage == 0) null else currentPage - 1,
                nextKey = if (users.content.isEmpty()) null else currentPage + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PlanetRankingUser>): Int? {
        return state.anchorPosition
    }
}