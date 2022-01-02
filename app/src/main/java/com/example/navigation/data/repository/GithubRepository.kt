package com.example.navigation.data.repository

import androidx.paging.PagingData
import com.example.navigation.data.entity.GithubRepositoryData
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    fun getUserRepositories(id: String): Flow<PagingData<GithubRepositoryData>>
}
