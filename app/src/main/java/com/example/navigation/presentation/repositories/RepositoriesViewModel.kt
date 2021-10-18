package com.example.navigation.presentation.repositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navigation.data.entity.GithubRepositoryData
import com.example.navigation.data.repository.GithubRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val githubRepository: GithubRepositoryImpl
) : ViewModel() {
    private val _repositories = MutableSharedFlow<List<GithubRepositoryData>>()
    val repositories = _repositories.asSharedFlow()

    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    fun getRepositories(id: String) {
        viewModelScope.launch {
            githubRepository.getUserRepositories(id)
                .onStart {
                    _loading.emit(true)
                }.onCompletion {
                    _loading.emit(false)
                }.collect { list ->
                    _repositories.emit(list)
                }
        }
    }
}
