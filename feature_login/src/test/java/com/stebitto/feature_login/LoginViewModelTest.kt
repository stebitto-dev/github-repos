package com.stebitto.feature_login

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

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var loginViewModel: LoginViewModel

    private val initialState = LoginState()
    private val loadingState = LoginState(isLoading = true, isLoggedIn = false, errorMessage = null)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        loginViewModel = LoginViewModel(initialState)
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
    fun `login loading`() = runTest {
        loginViewModel.dispatch(LoginIntent.LoginButtonClicked)
        assertEquals(loadingState, loginViewModel.state.value)
    }

    @Test
    fun `login success`() = runTest {
        val stateSuccess = LoginState(isLoading = false, isLoggedIn = true, errorMessage = null)
        loginViewModel.dispatch(LoginIntent.LoginSuccess)
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