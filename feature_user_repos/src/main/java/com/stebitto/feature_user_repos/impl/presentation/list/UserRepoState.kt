package com.stebitto.feature_user_repos.impl.presentation.list

import com.stebitto.common.api.State
import com.stebitto.feature_user_repos.impl.models.UserRepoPresentation

internal data class UserRepoState(
    val isLoading: Boolean = false,
    val repos: List<UserRepoPresentation> = emptyList(),
    val errorMessage: String? = null
) : State
