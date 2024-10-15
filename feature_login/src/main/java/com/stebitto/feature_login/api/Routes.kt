package com.stebitto.feature_login.api

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stebitto.feature_login.impl.LoginScreen

enum class LoginRoutes {
    LOGIN
}

fun NavGraphBuilder.loginRoutes(
    onLoginSuccess: () -> Unit
) {
    composable(LoginRoutes.LOGIN.name) {
        LoginScreen(
            onLoginSuccess = onLoginSuccess
        )
    }
}