package com.stebitto.github_repos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.stebitto.common.api.theme.MyApplicationTheme
import com.stebitto.feature_login.api.LoginRoutes
import com.stebitto.feature_login.api.loginRoutes
import com.stebitto.feature_user_repos.api.UserReposRoutes
import com.stebitto.feature_user_repos.api.userReposRoutes

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                SharedTransitionLayout {
                    val navController = rememberNavController()
                    val isUserLoggedIn = FirebaseAuth.getInstance().currentUser != null

                    NavHost(
                        navController = navController,
                        startDestination = if (isUserLoggedIn) UserReposRoutes.LIST.name else LoginRoutes.LOGIN.name,
                    ) {
                        loginRoutes(
                            sharedTransitionScope = this@SharedTransitionLayout,
                            onLoginSuccess = {
                                navController.navigate(UserReposRoutes.LIST.name) {
                                    popUpTo(LoginRoutes.LOGIN.name) { inclusive = true }
                                }
                            }
                        )
                        userReposRoutes(
                            sharedTransitionScope = this@SharedTransitionLayout,
                            onRepoClick = { owner, repoName ->
                                navController.navigate("${UserReposRoutes.DETAIL.name}/$owner/$repoName")
                            },
                            onNavigateBack = { navController.popBackStack() },
                            onSignOut = {
                                navController.navigate(LoginRoutes.LOGIN.name) {
                                    popUpTo(UserReposRoutes.LIST.name) { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}