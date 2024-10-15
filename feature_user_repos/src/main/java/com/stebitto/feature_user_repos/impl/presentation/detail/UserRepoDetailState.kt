package com.stebitto.feature_user_repos.impl.presentation.detail

import com.stebitto.common.api.State
import com.stebitto.feature_user_repos.impl.models.UserRepoDetailPresentation

internal data class UserRepoDetailState(
    val isLoading: Boolean = false,
    val userRepo: UserRepoDetailPresentation? = null,
    val errorMessage: String? = null
) : State