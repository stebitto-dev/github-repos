package com.stebitto.feature_user_repos

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.stebitto.feature_user_repos.impl.models.UserRepoDetailPresentation
import com.stebitto.feature_user_repos.impl.presentation.detail.RepositoryDetailScreen
import com.stebitto.feature_user_repos.impl.presentation.detail.TEST_REPO_DETAIL
import com.stebitto.feature_user_repos.impl.presentation.detail.TEST_REPO_DETAIL_ERROR_MESSAGE
import com.stebitto.feature_user_repos.impl.presentation.detail.TEST_REPO_DETAIL_LOADING_INDICATOR
import com.stebitto.feature_user_repos.impl.presentation.detail.TEST_REPO_DETAIL_NO_REPO_FOUND
import org.junit.Rule
import org.junit.Test

class UserRepoDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_showLoading() {
        composeTestRule.setContent {
            RepositoryDetailScreen(
                repo = null,
                isLoading = true,
                errorMessage = null
            )
        }
        composeTestRule.onNodeWithTag(TEST_REPO_DETAIL_LOADING_INDICATOR).assertIsDisplayed()
    }

    @Test
    fun test_showError() {
        composeTestRule.setContent {
            RepositoryDetailScreen(
                repo = null,
                isLoading = false,
                errorMessage = "Error message"
            )
        }
        composeTestRule.onNodeWithTag(TEST_REPO_DETAIL_ERROR_MESSAGE).assertIsDisplayed()
    }

    @Test
    fun test_showNoRepositoryFound() {
        composeTestRule.setContent {
            RepositoryDetailScreen(
                repo = null,
                isLoading = false,
                errorMessage = null
            )
        }
        composeTestRule.onNodeWithTag(TEST_REPO_DETAIL_NO_REPO_FOUND).assertIsDisplayed()
    }

    @Test
    fun test_showRepositoryDetail() {
        composeTestRule.setContent {
            RepositoryDetailScreen(
                repo = UserRepoDetailPresentation(1, "Repo 1", "octocat", "Description 1", "Kotlin", false, "", 2, 1, 0, true),
                isLoading = false,
                errorMessage = null
            )
        }
        composeTestRule.onNodeWithTag(TEST_REPO_DETAIL).assertIsDisplayed()
    }
}