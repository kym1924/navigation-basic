package com.example.navigation.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.navigation.data.api.GithubService
import com.example.navigation.data.entity.GithubRepositoryData

class GithubPagingSource(
    private val service: GithubService,
    private val id: String
) : PagingSource<Int, GithubRepositoryData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepositoryData> {
        val position = params.key ?: 1
        return try {
            val response = service.getUserRepositories(id, position, params.loadSize)
            LoadResult.Page(
                data = response,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + (params.loadSize / 30)
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GithubRepositoryData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
