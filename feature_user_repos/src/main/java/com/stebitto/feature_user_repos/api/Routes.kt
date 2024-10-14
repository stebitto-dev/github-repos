package com.stebitto.feature_user_repos.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stebitto.feature_user_repos.impl.presentation.UserRepoScreen

enum class UserReposRoutes {
    LIST, DETAIL
}

fun NavGraphBuilder.userReposRoutes() {
    composable(UserReposRoutes.LIST.name) {
        UserRepoScreen()
    }
    composable(UserReposRoutes.DETAIL.name) {
        TODO()
    }
}