package com.stebitto.feature_user_repos.impl.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stebitto.common.api.MVIViewModel
import com.stebitto.feature_user_repos.api.GithubRepository
import com.stebitto.feature_user_repos.impl.data.usecases.GetGithubUserRepoListUseCase
import com.stebitto.feature_user_repos.impl.data.usecases.SignOutUseCase
import com.stebitto.feature_user_repos.impl.models.toPresentationModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class UserRepoViewModel(
    private val githubRepository: GithubRepository,
    private val getGithubUserRepoListUseCase: GetGithubUserRepoListUseCase,
    private val signOutUseCase: SignOutUseCase,
    initialState: UserRepoState = UserRepoState()
) : MVIViewModel<UserRepoState, UserRepoIntent, UserRepoEffect>, ViewModel() {

    private val _state = MutableStateFlow(initialState)
    override val state: StateFlow<UserRepoState>
        get() = _state.asStateFlow()

    private val _sideEffects = Channel<UserRepoEffect>()
    override val sideEffects: Flow<UserRepoEffect>
        get() = _sideEffects.receiveAsFlow()

    override fun dispatch(intent: UserRepoIntent) {
        when (intent) {
            is UserRepoIntent.FetchRepos -> {
                _state.update { it.copy(isLoading = true) }
                fetchUserRepositories()
            }

            is UserRepoIntent.LoadLastVisitedRepo -> {
                fetchRepository(intent.owner, intent.repoName)
            }

            is UserRepoIntent.RepoClicked -> {
                _sideEffects.trySend(UserRepoEffect.NavigateToRepoDetail(intent.owner, intent.repoName))
            }

            UserRepoIntent.SignOut -> {
                signOut()
            }
        }
    }

    private fun fetchUserRepositories() {
        viewModelScope.launch {
            getGithubUserRepoListUseCase()
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

    private fun fetchRepository(owner: String, repoName: String) {
        viewModelScope.launch {
            githubRepository.getUserRepoByName(owner, repoName)
                .onSuccess { userRepoDetailDTO ->
                    val repoListUpdated = state.value.repos.toMutableList().map { repo ->
                        if (repo.name == userRepoDetailDTO?.name) {
                            userRepoDetailDTO.toPresentationModel()
                        } else {
                            repo
                        }
                    }
                    _state.update {
                        UserRepoState(
                            isLoading = false,
                            repos = repoListUpdated,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { exception -> _state.update { UserRepoState(errorMessage = exception.message) } }
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            signOutUseCase()
                .onSuccess { _sideEffects.trySend(UserRepoEffect.SignOutSuccessful) }
                .onFailure { exception -> _state.update { UserRepoState(errorMessage = exception.message) } }
        }
    }
}