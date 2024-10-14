package com.stebitto.feature_user_repos

import com.stebitto.common.api.MainDispatcherRule
import com.stebitto.common.api.models.UserRepoDTO
import com.stebitto.feature_user_repos.api.GithubUserRepoUseCase
import com.stebitto.feature_user_repos.impl.models.UserRepoPresentation
import com.stebitto.feature_user_repos.impl.presentation.UserRepoIntent
import com.stebitto.feature_user_repos.impl.presentation.UserRepoState
import com.stebitto.feature_user_repos.impl.presentation.UserRepoViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UserRepoViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var githubUserRepoUseCase: GithubUserRepoUseCase

    private lateinit var viewModel: UserRepoViewModel
    private val initialState = UserRepoState()

    private val fakeReposDTO = listOf(
        UserRepoDTO("Repo 1", "Description 1", "Kotlin", 2),
        UserRepoDTO("Repo 2", "Description 2", "Java", 1)
    )

    private val fakeReposPresentation = listOf(
        UserRepoPresentation("Repo 1", "Description 1", "Kotlin", 2),
        UserRepoPresentation("Repo 2", "Description 2", "Java", 1)
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = UserRepoViewModel(githubUserRepoUseCase, initialState)
    }

    @Test
    fun `test initial state`() = runTest {
        assertEquals(initialState, viewModel.state.value)
    }

    @Test
    fun `dispatch FetchRepos, test success state`() = runTest {
        val successState = UserRepoState(isLoading = false, repos = fakeReposPresentation, errorMessage = null)

        Mockito.`when`(githubUserRepoUseCase()).thenReturn(Result.success(fakeReposDTO))

        viewModel.dispatch(UserRepoIntent.FetchRepos)
        assertEquals(successState, viewModel.state.value)
    }

    @Test
    fun `dispatch FetchRepos, test failure state`() = runTest {
        val errorMessage = "Error message"
        val failureState = UserRepoState(isLoading = false, repos = emptyList(), errorMessage = errorMessage)

        Mockito.`when`(githubUserRepoUseCase()).thenReturn(Result.failure(Exception(errorMessage)))

        viewModel.dispatch(UserRepoIntent.FetchRepos)
        assertEquals(failureState, viewModel.state.value)
    }
}