package com.stebitto.feature_user_repos.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stebitto.feature_user_repos.impl.presentation.detail.UserRepoDetailScreen
import com.stebitto.feature_user_repos.impl.presentation.list.UserRepoScreen

enum class UserReposRoutes {
    LIST, DETAIL
}

fun NavGraphBuilder.userReposRoutes(
    onRepoClick: (owner: String, repoName: String) -> Unit = { _, _ -> }
) {
    composable(UserReposRoutes.LIST.name) {
        UserRepoScreen(
            onRepoClick = onRepoClick
        )
    }
    composable("${UserReposRoutes.DETAIL.name}/{owner}/{repoName}") { backStackEntry ->
        val owner = backStackEntry.arguments?.getString("repoFullName")
        val repoName = backStackEntry.arguments?.getString("repoName")
        UserRepoDetailScreen(owner = owner ?: "", repoName = repoName ?: "")
    }
}