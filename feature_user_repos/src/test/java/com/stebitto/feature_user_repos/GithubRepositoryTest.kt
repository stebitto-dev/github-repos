package com.stebitto.feature_user_repos

import com.stebitto.common.api.MainDispatcherRule
import com.stebitto.feature_user_repos.impl.data.GithubLocalSource
import com.stebitto.feature_user_repos.impl.data.GithubRepositoryImpl
import com.stebitto.feature_user_repos.impl.data.GithubRemoteSource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GithubRepositoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var githubRemoteSource: GithubRemoteSource
    @Mock
    private lateinit var githubLocalSource: GithubLocalSource

    private lateinit var githubRepository: GithubRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        githubRepository = GithubRepositoryImpl(githubRemoteSource, githubLocalSource)
    }

    @Test
    fun `getUserRepos should return success result with empty list`() = runTest {
        Mockito.`when`(githubRemoteSource.getUserRepos("")).thenReturn(emptyList())
        val result = githubRepository.getUserRepos("")
        assert(result.isSuccess)
        assert(result.getOrNull()?.isEmpty() == true)
    }

    @Test
    fun `getUserRepos should return success result with non-empty list`() = runTest {
        Mockito.`when`(githubRemoteSource.getUserRepos("")).thenReturn(fakeReposRemote)
        val result = githubRepository.getUserRepos("")
        assert(result.isSuccess)
        assertEquals(result.getOrNull(), fakeReposDTO)
    }

    @Test
    fun `getUserRepos should return success result with filtered list`() = runTest {
        Mockito.`when`(githubRemoteSource.getUserRepos("")).thenReturn(fakeReposRemoteWithNullId)
        val result = githubRepository.getUserRepos("")
        assert(result.isSuccess)
        assertEquals(result.getOrNull(), fakeReposDTO)
    }

    @Test
    fun `getUserRepos should return failure result`() = runTest {
        Mockito.`when`(githubRemoteSource.getUserRepos("")).thenThrow(RuntimeException())
        val result = githubRepository.getUserRepos("")
        assert(result.isFailure)
    }

    @Test
    fun `getUserRepoById should return success result with null value`() = runTest {
        Mockito.`when`(githubLocalSource.getUserRepoById(1)).thenReturn(null)
        val result = githubRepository.getUserRepoById(1)
        assert(result.isSuccess)
        assertEquals(result.getOrNull(), null)
    }

    @Test
    fun `getUserRepoById should return success result with non-null value`() = runTest {
        Mockito.`when`(githubLocalSource.getUserRepoById(1)).thenReturn(userRepoDBEntity1)
        val result = githubRepository.getUserRepoById(1)
        assert(result.isSuccess)
        assertEquals(result.getOrNull(), userRepoDTO1)
    }

    @Test
    fun `getUserRepoById should return failure result`() = runTest {
        Mockito.`when`(githubLocalSource.getUserRepoById(1)).thenThrow(RuntimeException())
        val result = githubRepository.getUserRepoById(1)
        assert(result.isFailure)
    }
}