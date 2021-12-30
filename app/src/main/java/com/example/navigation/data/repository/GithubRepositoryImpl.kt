package com.example.navigation.data.repository

import com.example.navigation.data.api.GithubService
import com.example.navigation.data.entity.GithubRepositoryData
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubService: GithubService
) : GithubRepository {
    override fun getUserRepositories(id: String) = flow<List<GithubRepositoryData>> {
        runCatching {
            githubService.getUserRepositories(id)
        }.onSuccess { repositories ->
            emit(repositories)
        }.onFailure {
            val emptyList: List<GithubRepositoryData> = emptyList()
            emit(emptyList)
        }
    }
}
