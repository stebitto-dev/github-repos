package com.stebitto.feature_login.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stebitto.common.api.MVIViewModel
import com.stebitto.common.api.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class LoginViewModel(
    private val userRepository: UserRepository,
    initialState: LoginState = LoginState()
) : MVIViewModel<LoginState, LoginIntent>, ViewModel() {

    private val _state = MutableStateFlow(initialState)
    override val state: StateFlow<LoginState>
        get() = _state.asStateFlow()

    override fun dispatch(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.LoginButtonClicked -> _state.update {
                it.copy(isLoading = true, errorMessage = null)
            }
            is LoginIntent.LoginSuccess -> {
                viewModelScope.launch {
                    userRepository.saveGithubToken(intent.accessToken)
                }

                _state.update {
                    LoginState(
                        isLoading = false,
                        isLoggedIn = true,
                        errorMessage = null
                    )
                }
            }
            is LoginIntent.LoginFailed -> _state.update {
                LoginState(
                    isLoading = false,
                    isLoggedIn = false,
                    errorMessage = intent.errorMessage
                )
            }
        }
    }
}