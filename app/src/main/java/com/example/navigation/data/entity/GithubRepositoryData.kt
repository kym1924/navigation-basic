package com.example.navigation.data.entity

import com.google.gson.annotations.SerializedName

data class GithubRepositoryData(
    @SerializedName("name")
    val name: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("stargazers_count")
    val stargazersCount: Int
)
