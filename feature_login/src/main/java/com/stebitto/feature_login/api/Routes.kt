package com.stebitto.feature_login.api

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stebitto.feature_login.impl.LoginScreen

enum class LoginRoutes {
    LOGIN
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.loginRoutes(
    sharedTransitionScope: SharedTransitionScope,
    onLoginSuccess: () -> Unit
) {
    composable(LoginRoutes.LOGIN.name) {
        LoginScreen(
            sharedTransitionScope = sharedTransitionScope,
            animatedContentScope = this@composable,
            onLoginSuccess = onLoginSuccess
        )
    }
}