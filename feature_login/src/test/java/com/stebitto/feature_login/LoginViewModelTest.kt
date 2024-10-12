package com.stebitto.feature_login

import app.cash.turbine.test
import com.stebitto.common.api.models.LoginDTO
import com.stebitto.feature_login.api.data.GithubLoginUseCase
import com.stebitto.feature_login.impl.presentation.LoginIntent
import com.stebitto.feature_login.impl.presentation.LoginState
import com.stebitto.feature_login.impl.presentation.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var loginUseCase: GithubLoginUseCase

    private lateinit var loginViewModel: LoginViewModel

    private val initialState = LoginState()
    private val loadingState = LoginState(isLoading = true, isLoggedIn = false, errorMessage = null)

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        loginViewModel = LoginViewModel(loginUseCase, initialState)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login initial state`() = runTest {
        assertEquals(initialState, loginViewModel.state.value)
    }

    @Test
    fun `login success`() = runTest {
        val username = "testuser"
        val password = "password"
        val resultSuccess = Result.success(LoginDTO(true))
        val stateSuccess = LoginState(isLoading = false, isLoggedIn = true, errorMessage = null)

        Mockito.`when`(loginUseCase(username, password)).thenReturn(resultSuccess)

        loginViewModel.state.test {
            assertEquals(initialState, awaitItem())
            loginViewModel.dispatch(LoginIntent.Login(username, password))
            assertEquals(loadingState, awaitItem())
            assertEquals(stateSuccess, awaitItem())
        }
    }

    @Test
    fun `login failure`() = runTest {
        val username = "testuser"
        val password = "wrongpassword"
        val errorMessage = "Login failed"
        val resultFailure = Result.failure<LoginDTO>(Exception(errorMessage))
        val stateFailure = LoginState(isLoading = false, isLoggedIn = false, errorMessage = errorMessage)

        Mockito.`when`(loginUseCase(username, password)).thenReturn(resultFailure)

        loginViewModel.state.test {
            assertEquals(initialState, awaitItem())
            loginViewModel.dispatch(LoginIntent.Login(username, password))
            assertEquals(loadingState, awaitItem())
            assertEquals(stateFailure, awaitItem())
        }
    }
}