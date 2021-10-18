package com.example.navigation.data.api

import com.example.navigation.data.entity.GithubRepositoryData
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("users/{id}/repos")
    suspend fun getUserRepositories(
        @Path("id") id: String
    ): List<GithubRepositoryData>
}
