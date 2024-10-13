package com.stebitto.feature_login.impl.presentation

import android.app.Activity
import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.stebitto.common.api.theme.MyApplicationTheme
import com.stebitto.feature_login.R
import org.koin.androidx.compose.koinViewModel

const val TEST_ERROR_MESSAGE = "TEST_ERROR_MESSAGE"
const val TEST_LOADING_INDICATOR = "TEST_LOADING_INDICATOR"
const val TEST_BUTTON_LOGIN = "TEST_BUTTON_LOGIN"

@Composable
internal fun LoginScreen(
    loginViewmodel: LoginViewModel = koinViewModel(),
    onLoginSuccess: () -> Unit = {}
) {
    val uiState = loginViewmodel.state.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = FirebaseAuthUIActivityResultContract(),
        onResult = { res ->
            val response = res.idpResponse
            if (res.resultCode == Activity.RESULT_OK) {
                // Login successful
                loginViewmodel.dispatch(LoginIntent.LoginSuccess)
                onLoginSuccess()
            } else {
                // Login failed
                loginViewmodel.dispatch(LoginIntent.LoginFailed(response?.error?.message))
            }
        }
    )

    val githubProvider = AuthUI.IdpConfig.GitHubBuilder()
        .setCustomParameters(mapOf("allow_signup" to "false"))
        .build()

    val signInIntent = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(listOf(githubProvider))
        .build()

    LoginCard(
        isLoading = uiState.value.isLoading,
        errorMessage = uiState.value.errorMessage,
        onLoginClick = {
            loginViewmodel.dispatch(LoginIntent.LoginButtonClicked)
            launcher.launch(signInIntent)
        }
    )
}

@Composable
internal fun LoginCard(
    isLoading: Boolean,
    errorMessage: String?,
    onLoginClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.inversePrimary
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.github_logo),
                    contentDescription = stringResource(R.string.github_logo_content_description),
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(32.dp))

                if (!errorMessage.isNullOrBlank()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .testTag(TEST_ERROR_MESSAGE)
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.testTag(TEST_LOADING_INDICATOR)
                        )
                    } else {
                        Button(
                            onClick = { onLoginClick() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onBackground,
                            ),
                            modifier = Modifier.testTag(TEST_BUTTON_LOGIN)
                        ) {
                            Text(stringResource(R.string.login_button_text))
                        }
                    }
                }
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
internal fun LoginCardPreview() {
    MyApplicationTheme {
        LoginCard(isLoading = false, errorMessage = null)
    }
}