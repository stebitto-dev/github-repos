package com.stebitto.feature_user_repos

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.stebitto.feature_user_repos.impl.models.UserRepoPresentation
import com.stebitto.feature_user_repos.impl.presentation.list.TEST_REPO_EMPTY_LIST
import com.stebitto.feature_user_repos.impl.presentation.list.TEST_REPO_LIST_COLUMN
import com.stebitto.feature_user_repos.impl.presentation.list.TEST_REPO_LIST_ERROR_MESSAGE
import com.stebitto.feature_user_repos.impl.presentation.list.TEST_REPO_LIST_LOADING_INDICATOR
import com.stebitto.feature_user_repos.impl.presentation.list.UserRepoList
import org.junit.Rule
import org.junit.Test

class UserRepoScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_showLoading() {
        composeTestRule.setContent {
            UserRepoList(
                repos = emptyList(),
                isLoading = true,
                errorMessage = null
            )
        }
        composeTestRule.onNodeWithTag(TEST_REPO_LIST_LOADING_INDICATOR).assertIsDisplayed()
    }

    @Test
    fun test_showError() {
        composeTestRule.setContent {
            UserRepoList(
                repos = emptyList(),
                isLoading = false,
                errorMessage = "Error message"
            )
        }
        composeTestRule.onNodeWithTag(TEST_REPO_LIST_ERROR_MESSAGE).assertIsDisplayed()
    }

    @Test
    fun test_showNoRepositoryFound() {
        composeTestRule.setContent {
            UserRepoList(
                repos = emptyList(),
                isLoading = false,
                errorMessage = null
            )
        }
        composeTestRule.onNodeWithTag(TEST_REPO_EMPTY_LIST).assertIsDisplayed()
    }

    @Test
    fun test_showRepositoryList() {
        composeTestRule.setContent {
            UserRepoList(
                repos = listOf(UserRepoPresentation(0, "", "", "", "", 0)),
                isLoading = false,
                errorMessage = null
            )
        }
        composeTestRule.onNodeWithTag(TEST_REPO_LIST_COLUMN).assertIsDisplayed()
    }
}