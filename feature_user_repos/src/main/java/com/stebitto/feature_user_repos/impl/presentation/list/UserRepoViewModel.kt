package com.stebitto.feature_user_repos.impl.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stebitto.common.api.MVIViewModel
import com.stebitto.feature_user_repos.api.GetGithubUserRepoUseCase
import com.stebitto.feature_user_repos.impl.models.toPresentationModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class UserRepoViewModel(
    private val getGithubUserRepoUseCase: GetGithubUserRepoUseCase,
    initialState: UserRepoState = UserRepoState()
) : MVIViewModel<UserRepoState, UserRepoIntent>, ViewModel() {

    private val _state = MutableStateFlow(initialState)
    override val state: StateFlow<UserRepoState>
        get() = _state.asStateFlow()

    override fun dispatch(intent: UserRepoIntent) {
        viewModelScope.launch {
            when (intent) {
                is UserRepoIntent.FetchRepos -> fetchUserRepositories()
            }
        }
    }

    private suspend fun fetchUserRepositories() {
        _state.update { it.copy(isLoading = true) }
        getGithubUserRepoUseCase()
            .onSuccess { repos ->
                val reposPresentationModel = repos.map { it.toPresentationModel() }
                _state.update {
                    UserRepoState(
                        isLoading = false,
                        repos = reposPresentationModel,
                        errorMessage = null
                    )
                }
            }
            .onFailure { exception ->
                _state.update {
                    UserRepoState(
                        isLoading = false,
                        repos = emptyList(),
                        errorMessage = exception.message
                    )
                }
            }
    }
}