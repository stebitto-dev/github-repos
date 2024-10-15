package com.stebitto.feature_user_repos.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stebitto.feature_user_repos.impl.presentation.detail.UserRepoDetailScreen
import com.stebitto.feature_user_repos.impl.presentation.list.UserRepoScreen

enum class UserReposRoutes {
    LIST, DETAIL
}

fun NavGraphBuilder.userReposRoutes(
    onRepoClick: (id: Int) -> Unit = {}
) {
    composable(UserReposRoutes.LIST.name) {
        UserRepoScreen(
            onRepoClick = onRepoClick
        )
    }
    composable("${UserReposRoutes.DETAIL.name}/{repoId}") { backStackEntry ->
        val repoId = backStackEntry.arguments?.getString("repoId")
        UserRepoDetailScreen(repoId = repoId?.toInt() ?: 0)
    }
}