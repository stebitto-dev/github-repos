package com.stebitto.feature_login

import app.cash.turbine.turbineScope
import com.stebitto.common.api.MainDispatcherRule
import com.stebitto.common.api.UserRepository
import com.stebitto.feature_login.impl.LoginEffect
import com.stebitto.feature_login.impl.LoginIntent
import com.stebitto.feature_login.impl.LoginState
import com.stebitto.feature_login.impl.LoginViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var loginViewModel: LoginViewModel

    private val initialState = LoginState()
    private val loadingState = LoginState(isLoading = true, isLoggedIn = false, errorMessage = null)

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        loginViewModel = LoginViewModel(userRepository, initialState)
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
        turbineScope {
            val stateSuccess = LoginState(isLoading = false, isLoggedIn = true, errorMessage = null)
            val sideEffectReceiver = loginViewModel.sideEffects.testIn(backgroundScope)

            loginViewModel.dispatch(LoginIntent.LoginSuccess(""))

            assertEquals(stateSuccess, loginViewModel.state.value)
            assertEquals(LoginEffect.LoginSuccess, sideEffectReceiver.awaitItem())
            // Verify that the saveGithubTokenUseCase was called with the correct token
            verify(userRepository).saveGithubToken("")
        }
    }

    @Test
    fun `login failure`() = runTest {
        val errorMessage = "Login failed"
        val stateFailure = LoginState(isLoading = false, isLoggedIn = false, errorMessage = errorMessage)
        loginViewModel.dispatch(LoginIntent.LoginFailed(errorMessage))
        assertEquals(stateFailure, loginViewModel.state.value)
    }
}