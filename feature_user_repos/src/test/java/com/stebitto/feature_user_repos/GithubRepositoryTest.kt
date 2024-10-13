package com.stebitto.feature_user_repos

import com.stebitto.common.api.MainDispatcherRule
import com.stebitto.feature_user_repos.impl.data.GithubRepositoryImpl
import com.stebitto.feature_user_repos.impl.data.GithubRemoteSource
import kotlinx.coroutines.test.runTest
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

    private lateinit var githubRepository: GithubRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        githubRepository = GithubRepositoryImpl(githubRemoteSource)
    }

    @Test
    fun `getUserRepos should return success result`() = runTest {
        Mockito.`when`(githubRemoteSource.getUserRepos("")).thenReturn(emptyList())
        val result = githubRepository.getUserRepos("")
        assert(result.isSuccess)
    }

    @Test
    fun `getUserRepos should return failure result`() = runTest {
        Mockito.`when`(githubRemoteSource.getUserRepos("")).thenThrow(RuntimeException())
        val result = githubRepository.getUserRepos("")
        assert(result.isFailure)
    }
}