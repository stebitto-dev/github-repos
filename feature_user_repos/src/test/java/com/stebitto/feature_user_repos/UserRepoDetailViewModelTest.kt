package com.stebitto.feature_user_repos

import com.stebitto.common.api.MainDispatcherRule
import com.stebitto.feature_user_repos.api.GithubRepository
import com.stebitto.feature_user_repos.impl.data.usecases.StarGithubRepoUseCase
import com.stebitto.feature_user_repos.impl.data.usecases.UnstarGithubRepoUseCase
import com.stebitto.feature_user_repos.impl.presentation.detail.UserRepoDetailIntent
import com.stebitto.feature_user_repos.impl.presentation.detail.UserRepoDetailState
import com.stebitto.feature_user_repos.impl.presentation.detail.UserRepoDetailViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UserRepoDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var githubRepository: GithubRepository
    @Mock
    private lateinit var starGithubRepoUseCase: StarGithubRepoUseCase
    @Mock
    private lateinit var unstarGithubRepoUseCase: UnstarGithubRepoUseCase

    private lateinit var viewModel: UserRepoDetailViewModel
    private val initialState = UserRepoDetailState()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = UserRepoDetailViewModel(githubRepository, starGithubRepoUseCase, unstarGithubRepoUseCase, initialState)
    }

    @Test
    fun `test initial state`() {
        assertEquals(viewModel.state.value, initialState)
    }

    @Test
    fun `test loadUserRepo success`() = runTest {
        val successState = UserRepoDetailState(isLoading = false, userRepo = userRepoDetailPresentation, errorMessage = null)

        Mockito.`when`(githubRepository.getUserRepoByName(owner = "", name = "")).thenReturn(Result.success(userRepoDetailDTO))

        viewModel.dispatch(UserRepoDetailIntent.LoadUserRepo(owner = "", repoName = ""))
        assertEquals(successState, viewModel.state.value)
    }

    @Test
    fun `test loadUserRepo failure`() = runTest {
        val errorMessage = "Error message"
        val failureState = UserRepoDetailState(isLoading = false, userRepo = null, errorMessage = errorMessage)

        Mockito.`when`(githubRepository.getUserRepoByName(owner = "", name = "")).thenReturn(Result.failure(Exception(errorMessage)))

        viewModel.dispatch(UserRepoDetailIntent.LoadUserRepo(owner = "", repoName = ""))
        assertEquals(failureState, viewModel.state.value)
    }

    @Test
    fun `test starClicked success`() = runTest {
        val successState = UserRepoDetailState(isLoading = false, userRepo = userRepoDetailPresentation, errorMessage = null)

        Mockito.`when`(starGithubRepoUseCase(owner = "", repoName = "")).thenReturn(Result.success(userRepoDetailDTO))

        viewModel.dispatch(UserRepoDetailIntent.StarClicked(owner = "", repoName = "", isStarred = false))
        assertEquals(successState, viewModel.state.value)
    }

    @Test
    fun `test starClicked failure`() = runTest {
        val errorMessage = "Error message"
        val failureState = UserRepoDetailState(isLoading = false, userRepo = null, errorMessage = errorMessage)

        Mockito.`when`(starGithubRepoUseCase(owner = "", repoName = "")).thenReturn(Result.failure(Exception(errorMessage)))

        viewModel.dispatch(UserRepoDetailIntent.StarClicked(owner = "", repoName = "", isStarred = false))
        assertEquals(failureState, viewModel.state.value)
    }

    @Test
    fun `test unstarClicked success`() = runTest {
        val successState = UserRepoDetailState(isLoading = false, userRepo = userRepoDetailPresentation, errorMessage = null)

        Mockito.`when`(unstarGithubRepoUseCase(owner = "", repoName = "")).thenReturn(Result.success(userRepoDetailDTO))

        viewModel.dispatch(UserRepoDetailIntent.StarClicked(owner = "", repoName = "", isStarred = true))
        assertEquals(successState, viewModel.state.value)
    }

    @Test
    fun `test unstarClicked failure`() = runTest {
        val errorMessage = "Error message"
        val failureState = UserRepoDetailState(isLoading = false, userRepo = null, errorMessage = errorMessage)

        Mockito.`when`(unstarGithubRepoUseCase(owner = "", repoName = "")).thenReturn(Result.failure(Exception(errorMessage)))

        viewModel.dispatch(UserRepoDetailIntent.StarClicked(owner = "", repoName = "", isStarred = true))
        assertEquals(failureState, viewModel.state.value)
    }
}