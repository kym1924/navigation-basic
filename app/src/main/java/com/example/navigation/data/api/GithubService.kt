package com.example.navigation.data.api

import com.example.navigation.data.entity.GithubRepositoryData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    @GET("users/{id}/repos")
    suspend fun getUserRepositories(
        @Path("id") id: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<GithubRepositoryData>
}
