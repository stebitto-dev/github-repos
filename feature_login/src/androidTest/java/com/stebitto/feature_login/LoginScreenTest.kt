package com.stebitto.feature_login

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.stebitto.feature_login.impl.presentation.LoginCard
import com.stebitto.feature_login.impl.presentation.TEST_BUTTON_LOGIN
import com.stebitto.feature_login.impl.presentation.TEST_ERROR_MESSAGE
import com.stebitto.feature_login.impl.presentation.TEST_LOADING_INDICATOR
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_showLoading() {
        composeTestRule.setContent {
            LoginCard(username = "", password = "", error = "", isLoading = true)
        }
        composeTestRule.onNodeWithTag(TEST_LOADING_INDICATOR).assertIsDisplayed()
    }

    @Test
    fun test_showError() {
        composeTestRule.setContent {
            LoginCard(username = "", password = "", error = "Error message", isLoading = false)
        }
        composeTestRule.onNodeWithTag(TEST_ERROR_MESSAGE).assertIsDisplayed()
    }

    @Test
    fun test_showLoginButton() {
        composeTestRule.setContent {
            LoginCard(username = "", password = "", error = "", isLoading = false)
        }
        composeTestRule.onNodeWithTag(TEST_BUTTON_LOGIN).assertIsDisplayed()
    }
}