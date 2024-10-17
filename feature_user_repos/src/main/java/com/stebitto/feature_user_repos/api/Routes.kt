package com.stebitto.feature_user_repos.api

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stebitto.feature_user_repos.impl.presentation.detail.UserRepoDetailScreen
import com.stebitto.feature_user_repos.impl.presentation.list.UserRepoScreen

enum class UserReposRoutes {
    LIST, DETAIL
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.userReposRoutes(
    sharedTransitionScope: SharedTransitionScope,
    onRepoClick: (owner: String, repoName: String) -> Unit = { _, _ -> },
    onNavigateBack: () -> Unit = {},
    onSignOut: () -> Unit = {}
) {
    composable(UserReposRoutes.LIST.name) {
        UserRepoScreen(
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this@composable,
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
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this@composable,
            onNavigateBack = onNavigateBack,
            onSignOut = onSignOut
        )
    }
}