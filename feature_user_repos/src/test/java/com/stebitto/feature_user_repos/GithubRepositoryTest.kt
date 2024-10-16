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
    fun `getUserRepos should return success result with remote empty list`() = runTest {
        Mockito.`when`(githubRemoteSource.getUserRepos()).thenReturn(emptyList())
        val result = githubRepository.getUserRepos()
        assert(result.isSuccess)
        assert(result.getOrNull()?.isEmpty() == true)
    }

    @Test
    fun `getUserRepos should return success result with remote non-empty list`() = runTest {
        Mockito.`when`(githubRemoteSource.getUserRepos()).thenReturn(fakeReposRemote)
        val result = githubRepository.getUserRepos()
        assert(result.isSuccess)
        assertEquals(result.getOrNull(), fakeReposDTO)
    }

    @Test
    fun `getUserRepos should return success result with remote filtered list`() = runTest {
        Mockito.`when`(githubRemoteSource.getUserRepos()).thenReturn(fakeReposRemoteWithNullId)
        val result = githubRepository.getUserRepos()
        assert(result.isSuccess)
        assertEquals(result.getOrNull(), fakeReposDTO)
    }

    @Test
    fun `getUserRepos should return success result with local list`() = runTest {
        Mockito.`when`(githubRemoteSource.getUserRepos()).thenThrow(RuntimeException())
        Mockito.`when`(githubLocalSource.getUserRepos()).thenReturn(fakeReposDBEntity)
        val result = githubRepository.getUserRepos()
        assert(result.isSuccess)
        assertEquals(result.getOrNull(), fakeReposDTO)
    }

    @Test
    fun `getUserRepos should return success result with local empty list`() = runTest {
        Mockito.`when`(githubRemoteSource.getUserRepos()).thenThrow(RuntimeException())
        Mockito.`when`(githubLocalSource.getUserRepos()).thenReturn(emptyList())
        val result = githubRepository.getUserRepos()
        assert(result.isSuccess)
        assert(result.getOrNull()?.isEmpty() == true)
    }

    @Test
    fun `getUserRepos should return failure result`() = runTest {
        Mockito.`when`(githubRemoteSource.getUserRepos()).thenThrow(RuntimeException())
        Mockito.`when`(githubLocalSource.getUserRepos()).thenThrow(RuntimeException())
        val result = githubRepository.getUserRepos()
        assert(result.isFailure)
    }

    @Test
    fun `getUserRepoByName should return success result`() = runTest {
        Mockito.`when`(githubRemoteSource.getRepo("", "")).thenReturn(userRepoRemoteDetail)
        Mockito.`when`(githubRemoteSource.checkIfRepoStarred("", "")).thenReturn(true)
        val result = githubRepository.getUserRepoByName("", "")
        assert(result.isSuccess)
        assertEquals(result.getOrNull(), userRepoDetailDTO)
    }

    @Test
    fun `getUserRepoByName should return failure result if repo starred fails`() = runTest {
        Mockito.`when`(githubRemoteSource.getRepo("", "")).thenReturn(userRepoRemoteDetail)
        Mockito.`when`(githubRemoteSource.checkIfRepoStarred("", "")).thenThrow(RuntimeException())
        val result = githubRepository.getUserRepoByName("", "")
        assert(result.isFailure)
    }

    @Test
    fun `getUserRepoByName should return failure result if get repo fails`() = runTest {
        Mockito.`when`(githubRemoteSource.getRepo("", "")).thenThrow(RuntimeException())
        Mockito.`when`(githubLocalSource.getUserRepoByName("")).thenThrow(RuntimeException())
        val result = githubRepository.getUserRepoByName("", "")
        assert(result.isFailure)
    }

    @Test
    fun `starRepo should return success result`() = runTest {
        Mockito.`when`(githubRemoteSource.starRepo("", "")).thenReturn(Unit)
        val result = githubRepository.starRepo("", "")
        assert(result.isSuccess)
    }

    @Test
    fun `starRepo should return failure result`() = runTest {
        Mockito.`when`(githubRemoteSource.starRepo("", "")).thenThrow(RuntimeException())
        val result = githubRepository.starRepo("", "")
        assert(result.isFailure)
    }

    @Test
    fun `unstarRepo should return success result`() = runTest {
        Mockito.`when`(githubRemoteSource.unstarRepo("", "")).thenReturn(Unit)
        val result = githubRepository.unstarRepo("", "")
        assert(result.isSuccess)
    }

    @Test
    fun `unstarRepo should return failure result`() = runTest {
        Mockito.`when`(githubRemoteSource.unstarRepo("", "")).thenThrow(RuntimeException())
        val result = githubRepository.unstarRepo("", "")
        assert(result.isFailure)
    }
}