package com.stebitto.feature_user_repos

import com.stebitto.common.api.MainDispatcherRule
import com.stebitto.feature_user_repos.impl.data.usecases.GetGithubUserRepoListUseCase
import com.stebitto.feature_user_repos.impl.presentation.list.UserRepoIntent
import com.stebitto.feature_user_repos.impl.presentation.list.UserRepoState
import com.stebitto.feature_user_repos.impl.presentation.list.UserRepoViewModel
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
    private lateinit var getGithubUserRepoListUseCase: GetGithubUserRepoListUseCase

    private lateinit var viewModel: UserRepoViewModel
    private val initialState = UserRepoState()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = UserRepoViewModel(getGithubUserRepoListUseCase, initialState)
    }

    @Test
    fun `test initial state`() = runTest {
        assertEquals(initialState, viewModel.state.value)
    }

    @Test
    fun `dispatch FetchRepos, test success state`() = runTest {
        val successState = UserRepoState(isLoading = false, repos = fakeReposPresentation, errorMessage = null)

        Mockito.`when`(getGithubUserRepoListUseCase()).thenReturn(Result.success(fakeReposDTO))

        viewModel.dispatch(UserRepoIntent.FetchRepos)
        assertEquals(successState, viewModel.state.value)
    }

    @Test
    fun `dispatch FetchRepos, test failure state`() = runTest {
        val errorMessage = "Error message"
        val failureState = UserRepoState(isLoading = false, repos = emptyList(), errorMessage = errorMessage)

        Mockito.`when`(getGithubUserRepoListUseCase()).thenReturn(Result.failure(Exception(errorMessage)))

        viewModel.dispatch(UserRepoIntent.FetchRepos)
        assertEquals(failureState, viewModel.state.value)
    }
}