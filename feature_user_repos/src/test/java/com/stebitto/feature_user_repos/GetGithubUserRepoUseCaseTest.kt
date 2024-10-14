package com.stebitto.feature_user_repos

import com.stebitto.common.api.MainDispatcherRule
import com.stebitto.feature_user_repos.api.GithubRepository
import com.stebitto.common.api.GetGithubTokenUseCase
import com.stebitto.feature_user_repos.impl.data.GetGithubUserRepoUseCaseImpl
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class GetGithubUserRepoUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var githubRepository: GithubRepository
    @Mock
    private lateinit var getGithubTokenUseCase: GetGithubTokenUseCase

    private lateinit var githubUserRepoUseCase: GetGithubUserRepoUseCaseImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        githubUserRepoUseCase = GetGithubUserRepoUseCaseImpl(githubRepository, getGithubTokenUseCase)
    }

    @Test
    fun `invoke should return success result and save user repos`() = runTest {
        Mockito.`when`(getGithubTokenUseCase()).thenReturn(Result.success(""))
        Mockito.`when`(githubRepository.getUserRepos("")).thenReturn(Result.success(fakeReposDTO))
        val result = githubUserRepoUseCase()
        assert(result.isSuccess)
        // Verify that the user repos were saved to the repository
        verify(githubRepository).saveUserRepos(fakeReposDTO)
    }

    @Test
    fun `invoke should return failure result if token retrieval fails`() = runTest {
        Mockito.`when`(getGithubTokenUseCase()).thenReturn(Result.failure(RuntimeException()))
        val result = githubUserRepoUseCase()
        assert(result.isFailure)
    }

    @Test
    fun `invoke should return failure result if user repos retrieval fails`() = runTest {
        Mockito.`when`(getGithubTokenUseCase()).thenReturn(Result.success(""))
        Mockito.`when`(githubRepository.getUserRepos("")).thenReturn(Result.failure(RuntimeException()))
        val result = githubUserRepoUseCase()
        assert(result.isFailure)
    }
}