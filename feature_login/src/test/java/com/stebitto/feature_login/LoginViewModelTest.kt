package com.stebitto.feature_login

import com.stebitto.common.api.MainDispatcherRule
import com.stebitto.common.api.SaveGithubTokenUseCase
import com.stebitto.feature_login.impl.presentation.LoginIntent
import com.stebitto.feature_login.impl.presentation.LoginState
import com.stebitto.feature_login.impl.presentation.LoginViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var saveGithubTokenUseCase: SaveGithubTokenUseCase

    private lateinit var loginViewModel: LoginViewModel

    private val initialState = LoginState()
    private val loadingState = LoginState(isLoading = true, isLoggedIn = false, errorMessage = null)

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        loginViewModel = LoginViewModel(saveGithubTokenUseCase, initialState)
    }

    @Test
    fun `login initial state`() = runTest {
        assertEquals(initialState, loginViewModel.state.value)
    }

    @Test
    fun `login loading`() = runTest {
        loginViewModel.dispatch(LoginIntent.LoginButtonClicked)
        assertEquals(loadingState, loginViewModel.state.value)
    }

    @Test
    fun `login success`() = runTest {
        val stateSuccess = LoginState(isLoading = false, isLoggedIn = true, errorMessage = null)

        Mockito.`when`(saveGithubTokenUseCase("")).thenReturn(Unit)

        loginViewModel.dispatch(LoginIntent.LoginSuccess(""))
        assertEquals(stateSuccess, loginViewModel.state.value)
    }

    @Test
    fun `login failure`() = runTest {
        val errorMessage = "Login failed"
        val stateFailure = LoginState(isLoading = false, isLoggedIn = false, errorMessage = errorMessage)
        loginViewModel.dispatch(LoginIntent.LoginFailed(errorMessage))
        assertEquals(stateFailure, loginViewModel.state.value)
    }
}