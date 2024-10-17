package com.stebitto.feature_user_repos.impl.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stebitto.common.api.MVIViewModel
import com.stebitto.feature_user_repos.api.GithubRepository
import com.stebitto.feature_user_repos.impl.data.usecases.SignOutUseCase
import com.stebitto.feature_user_repos.impl.data.usecases.StarGithubRepoUseCase
import com.stebitto.feature_user_repos.impl.data.usecases.UnstarGithubRepoUseCase
import com.stebitto.feature_user_repos.impl.models.toDetailPresentationModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class UserRepoDetailViewModel(
    private val githubRepository: GithubRepository,
    private val starGithubRepoUseCase: StarGithubRepoUseCase,
    private val unstarGithubRepoUseCase: UnstarGithubRepoUseCase,
    private val signOutUseCase: SignOutUseCase,
    initialState: UserRepoDetailState = UserRepoDetailState()
) : MVIViewModel<UserRepoDetailState, UserRepoDetailIntent, UserRepoDetailEffect>, ViewModel() {

    private val _state = MutableStateFlow(initialState)
    override val state = _state.asStateFlow()

    private val _sideEffects = Channel<UserRepoDetailEffect>()
    override val sideEffects = _sideEffects.receiveAsFlow()

    override fun dispatch(intent: UserRepoDetailIntent) {
        when (intent) {
            is UserRepoDetailIntent.LoadUserRepo -> {
                _state.update { it.copy(isLoading = true) }
                loadUserRepo(owner = intent.owner, repoName = intent.repoName)
            }
            is UserRepoDetailIntent.StarClicked -> {
                _state.update { it.copy(isLoading = true) }
                starClicked(owner = intent.owner, repoName = intent.repoName, isStarred = intent.isStarred)
            }
            UserRepoDetailIntent.SignOut -> { signOut() }
        }
    }

    private fun loadUserRepo(owner: String, repoName: String) {
        viewModelScope.launch {
            githubRepository.getUserRepoByName(owner, repoName)
                .onSuccess { repo ->
                    _state.update {
                        UserRepoDetailState(
                            isLoading = false,
                            userRepo = repo?.toDetailPresentationModel(),
                            errorMessage = null
                        )
                    }
                }
                .onFailure { exception ->
                    _state.update {
                        UserRepoDetailState(
                            isLoading = false,
                            userRepo = null,
                            errorMessage = exception.message
                        )
                    }
                }
        }
    }

    private fun starClicked(owner: String, repoName: String, isStarred: Boolean) {
        viewModelScope.launch {
            if (isStarred) {
                unstarGithubRepoUseCase(owner, repoName)
            } else {
                starGithubRepoUseCase(owner, repoName)
            }
                .onSuccess { repo ->
                    _state.update {
                        UserRepoDetailState(
                            isLoading = false,
                            userRepo = repo?.toDetailPresentationModel(),
                            errorMessage = null
                        )
                    }
                }
                .onFailure { exception ->
                    _state.update {
                        UserRepoDetailState(
                            isLoading = false,
                            userRepo = null,
                            errorMessage = exception.message
                        )
                    }
                }
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            signOutUseCase()
                .onSuccess { _sideEffects.trySend(UserRepoDetailEffect.SignOutSuccessful) }
                .onFailure { exception -> _state.update { UserRepoDetailState(errorMessage = exception.message) } }
        }
    }
}