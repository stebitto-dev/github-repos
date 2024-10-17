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
    var lastVisitedRepoOwner: String? = null
    var lastVisitedRepoName: String? = null

    composable(UserReposRoutes.LIST.name) {
        UserRepoScreen(
            lastVisitedRepoOwner = lastVisitedRepoOwner,
            lastVisitedRepoName = lastVisitedRepoName,
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this@composable,
            onNavigateToRepo = { owner, repoName ->
                lastVisitedRepoOwner = owner
                lastVisitedRepoName = repoName
                onRepoClick(owner, repoName)
            },
            onNavigateBack = onNavigateBack,
            onSignOut = {
                lastVisitedRepoOwner = null
                lastVisitedRepoName = null
                onSignOut()
            }
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
            onSignOut = {
                lastVisitedRepoOwner = null
                lastVisitedRepoName = null
                onSignOut()
            }
        )
    }
}