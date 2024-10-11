package com.stebitto.feature_login.impl

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stebitto.feature_login.impl.presentation.LoginScreen

enum class LoginRoutes {
    LOGIN
}

fun NavGraphBuilder.loginRoutes(
    onLoginSuccess: () -> Unit,
    onNavigateBack: () -> Unit
) {
    composable(LoginRoutes.LOGIN.name) {
        LoginScreen(
            onLoginSuccess = onLoginSuccess,
            onNavigateBack = onNavigateBack
        )
    }
}