package com.stebitto.feature_user_repos

import com.stebitto.common.api.MainDispatcherRule
import com.stebitto.feature_user_repos.api.GithubRepository
import com.stebitto.feature_user_repos.impl.data.usecases.GetGithubUserRepoListUseCaseImpl
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class GetGithubUserRepoListUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var githubRepository: GithubRepository

    private lateinit var githubUserRepoUseCase: GetGithubUserRepoListUseCaseImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        githubUserRepoUseCase = GetGithubUserRepoListUseCaseImpl(githubRepository)
    }

    @Test
    fun `invoke should return success result and save user repos`() = runTest {
        Mockito.`when`(githubRepository.getUserRepos()).thenReturn(Result.success(fakeReposDTO))
        val result = githubUserRepoUseCase()
        assert(result.isSuccess)
        // Verify that the user repos were saved to the repository
        verify(githubRepository).saveUserRepos(fakeReposDTO)
    }

    @Test
    fun `invoke should return failure result`() = runTest {
        Mockito.`when`(githubRepository.getUserRepos()).thenReturn(Result.failure(RuntimeException()))
        val result = githubUserRepoUseCase()
        assert(result.isFailure)
    }
}