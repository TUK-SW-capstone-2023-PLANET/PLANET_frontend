package com.example.planet.data.dataSource

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.planet.TAG
import com.example.planet.data.remote.api.ApiService
import com.example.planet.data.remote.dto.ranking.University
import java.io.IOException
import javax.inject.Inject

class PlanetUserPagingSource @Inject constructor(
    private val apiService: ApiService
): PagingSource<Int, University>() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, University> {
        return try {
            val currentPage = params.key ?: 0
            val users = apiService.getAllUniversityRanking(
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

    override fun getRefreshKey(state: PagingState<Int, University>): Int? {
        return state.anchorPosition
    }
}