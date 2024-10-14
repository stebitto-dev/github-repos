package com.stebitto.feature_user_repos

import com.stebitto.common.api.MainDispatcherRule
import com.stebitto.feature_user_repos.api.GithubRepository
import com.stebitto.common.api.GetGithubTokenUseCase
import com.stebitto.feature_user_repos.impl.data.GithubUserRepoUseCaseImpl
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GithubUserRepoUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var githubRepository: GithubRepository
    @Mock
    private lateinit var getGithubTokenUseCase: GetGithubTokenUseCase

    private lateinit var githubUserRepoUseCase: GithubUserRepoUseCaseImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        githubUserRepoUseCase = GithubUserRepoUseCaseImpl(githubRepository, getGithubTokenUseCase)
    }

    @Test
    fun `invoke should return success result`() = runTest {
        Mockito.`when`(getGithubTokenUseCase()).thenReturn(Result.success(""))
        Mockito.`when`(githubRepository.getUserRepos("")).thenReturn(Result.success(emptyList()))
        val result = githubUserRepoUseCase()
        assert(result.isSuccess)
    }

    @Test
    fun `invoke should return failure result`() = runTest {
        Mockito.`when`(getGithubTokenUseCase()).thenReturn(Result.failure(RuntimeException()))
        val result = githubUserRepoUseCase()
        assert(result.isFailure)
    }

}