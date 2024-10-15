package com.stebitto.feature_user_repos.impl.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stebitto.common.api.MVIViewModel
import com.stebitto.feature_user_repos.api.GithubRepository
import com.stebitto.feature_user_repos.impl.models.toDetailPresentationModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class UserRepoDetailViewModel(
    private val githubRepository: GithubRepository,
    initialState: UserRepoDetailState = UserRepoDetailState()
) : MVIViewModel<UserRepoDetailState, UserRepoDetailIntent>, ViewModel() {

    private val _state = MutableStateFlow(initialState)
    override val state = _state.asStateFlow()

    override fun dispatch(intent: UserRepoDetailIntent) {
        when (intent) {
            is UserRepoDetailIntent.LoadUserRepo -> loadUserRepo(intent.id)
        }
    }

    private fun loadUserRepo(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            githubRepository.getUserRepoById(id)
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
}