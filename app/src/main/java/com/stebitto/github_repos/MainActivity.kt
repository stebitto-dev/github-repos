package com.stebitto.github_repos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.stebitto.common.api.theme.MyApplicationTheme
import com.stebitto.feature_login.api.LoginRoutes
import com.stebitto.feature_login.api.loginRoutes

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                Scaffold { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = LoginRoutes.LOGIN.name,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        loginRoutes(
                            onLoginSuccess = {}
                        )
                    }
                }
            }
        }
    }
}