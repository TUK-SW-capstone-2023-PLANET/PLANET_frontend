package com.example.planet.data.repository.paging

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.planet.TAG
import com.example.planet.data.remote.api.spring.MainApi
import com.example.planet.data.remote.dto.response.ranking.season.SeasonUser
import java.io.IOException
import javax.inject.Inject

class SeasonUserPagingSource @Inject constructor(
    private val mainApi: MainApi
): PagingSource<Int, SeasonUser>() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SeasonUser> {
        return try {
            Log.d(TAG, "SeasonUserPagingSource() 실행")
            val currentPage = params.key ?: 0
            val users = mainApi.getAllSeasonRanking(
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

    override fun getRefreshKey(state: PagingState<Int, SeasonUser>): Int? {
        return state.anchorPosition
    }
}