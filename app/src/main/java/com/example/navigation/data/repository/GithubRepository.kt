package com.example.navigation.data.repository

import com.example.navigation.data.entity.GithubRepositoryData
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    fun getUserRepositories(id: String): Flow<List<GithubRepositoryData>>
}
