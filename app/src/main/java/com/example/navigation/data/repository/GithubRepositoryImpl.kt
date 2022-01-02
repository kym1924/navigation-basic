package com.example.navigation.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.navigation.data.api.GithubService
import com.example.navigation.data.entity.GithubRepositoryData
import com.example.navigation.data.paging.GithubPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubService: GithubService
) : GithubRepository {
    override fun getUserRepositories(id: String): Flow<PagingData<GithubRepositoryData>> {
        return Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false),
            pagingSourceFactory = { GithubPagingSource(githubService, id) }
        ).flow
    }
}
