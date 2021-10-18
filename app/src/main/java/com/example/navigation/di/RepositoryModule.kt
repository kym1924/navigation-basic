package com.example.navigation.di

import com.example.navigation.data.api.GithubService
import com.example.navigation.data.repository.GithubRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideGithubRepository(githubService: GithubService) =
        GithubRepositoryImpl(githubService)
}
