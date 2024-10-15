package com.stebitto.github_repos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.stebitto.common.api.theme.MyApplicationTheme
import com.stebitto.feature_login.api.LoginRoutes
import com.stebitto.feature_login.api.loginRoutes
import com.stebitto.feature_user_repos.api.UserReposRoutes
import com.stebitto.feature_user_repos.api.userReposRoutes

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                Scaffold { innerPadding ->
                    val navController = rememberNavController()

                    val isUserLoggedIn = FirebaseAuth.getInstance().currentUser != null

                    NavHost(
                        navController = navController,
                        startDestination = if (isUserLoggedIn) UserReposRoutes.LIST.name else LoginRoutes.LOGIN.name,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        loginRoutes(
                            onLoginSuccess = { navController.navigate(UserReposRoutes.LIST.name) }
                        )
                        userReposRoutes(
                            onRepoClick = { owner, repoName ->
                                navController.navigate("${UserReposRoutes.DETAIL.name}/$owner/$repoName")
                            }
                        )
                    }
                }
            }
        }
    }
}