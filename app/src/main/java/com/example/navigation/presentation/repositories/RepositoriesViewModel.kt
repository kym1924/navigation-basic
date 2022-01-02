package com.example.navigation.presentation.repositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.navigation.data.entity.GithubRepositoryData
import com.example.navigation.data.repository.GithubRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val githubRepository: GithubRepositoryImpl
) : ViewModel() {
    fun pagingRepositories(id: String): Flow<PagingData<GithubRepositoryData>> {
        return githubRepository.getUserRepositories(id).cachedIn(viewModelScope)
    }
}
