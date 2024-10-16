package com.stebitto.feature_user_repos

import com.stebitto.common.api.MainDispatcherRule
import com.stebitto.feature_user_repos.api.GithubRepository
import com.stebitto.feature_user_repos.impl.data.usecases.UnstarGithubRepoUseCase
import com.stebitto.feature_user_repos.impl.data.usecases.UnstarGithubRepoUseCaseImpl
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UnstarGithubRepoUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var githubRepository: GithubRepository

    private lateinit var unstarGithubRepoUseCase: UnstarGithubRepoUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        unstarGithubRepoUseCase = UnstarGithubRepoUseCaseImpl(githubRepository)
    }

    @Test
    fun `invoke should return success result`() = runTest {
        Mockito.`when`(githubRepository.unstarRepo("owner", "repoName")).thenReturn(Result.success(Unit))
        Mockito.`when`(githubRepository.getUserRepoByName("owner", "repoName")).thenReturn(Result.success(userRepoDetailDTO))
        val result = unstarGithubRepoUseCase("owner", "repoName")
        assert(result.isSuccess)
        assertEquals(result.getOrNull(), userRepoDetailDTO)
    }

    @Test
    fun `invoke should return success result if getUserRepoByName returns null`() = runTest {
        Mockito.`when`(githubRepository.unstarRepo("owner", "repoName")).thenReturn(Result.success(Unit))
        Mockito.`when`(githubRepository.getUserRepoByName("owner", "repoName")).thenReturn(Result.success(null))
        val result = unstarGithubRepoUseCase("owner", "repoName")
        assert(result.isSuccess)
        assertEquals(result.getOrNull(), null)
    }

    @Test
    fun `invoke should return failure result if star repo fails`() = runTest {
        Mockito.`when`(githubRepository.unstarRepo("owner", "repoName")).thenReturn(Result.failure(RuntimeException()))
        val result = unstarGithubRepoUseCase("owner", "repoName")
        assert(result.isFailure)
    }

    @Test
    fun `invoke should return failure result if getUserRepoByName fails`() = runTest {
        Mockito.`when`(githubRepository.unstarRepo("owner", "repoName")).thenReturn(Result.success(Unit))
        Mockito.`when`(githubRepository.getUserRepoByName("owner", "repoName")).thenReturn(Result.failure(RuntimeException()))
        val result = unstarGithubRepoUseCase("owner", "repoName")
        assert(result.isFailure)
    }
}