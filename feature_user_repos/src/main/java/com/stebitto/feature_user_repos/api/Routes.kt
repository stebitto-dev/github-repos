package com.stebitto.feature_user_repos.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stebitto.feature_user_repos.impl.presentation.detail.UserRepoDetailScreen
import com.stebitto.feature_user_repos.impl.presentation.list.UserRepoScreen

enum class UserReposRoutes {
    LIST, DETAIL
}

fun NavGraphBuilder.userReposRoutes(
    onRepoClick: (owner: String, repoName: String) -> Unit = { _, _ -> },
    onNavigateBack: () -> Unit = {},
    onSignOut: () -> Unit = {}
) {
    composable(UserReposRoutes.LIST.name) {
        UserRepoScreen(
            onNavigateToRepo = onRepoClick,
            onNavigateBack = onNavigateBack,
            onSignOut = onSignOut
        )
    }
    composable("${UserReposRoutes.DETAIL.name}/{owner}/{repoName}") { backStackEntry ->
        val owner = backStackEntry.arguments?.getString("owner")
        val repoName = backStackEntry.arguments?.getString("repoName")
        UserRepoDetailScreen(
            owner = owner ?: "",
            repoName = repoName ?: "",
            onNavigateBack = onNavigateBack,
            onSignOut = onSignOut
        )
    }
}