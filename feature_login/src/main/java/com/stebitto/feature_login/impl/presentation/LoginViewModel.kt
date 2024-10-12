package com.stebitto.feature_login.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stebitto.common.api.MVIViewModel
import com.stebitto.feature_login.api.data.GithubLoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class LoginViewModel(
    private val loginUseCase: GithubLoginUseCase,
    initialState: LoginState = LoginState()
) : MVIViewModel<LoginState, LoginIntent>, ViewModel() {

    private val _state = MutableStateFlow(initialState)
    override val state: StateFlow<LoginState>
        get() = _state.asStateFlow()

    override fun dispatch(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.Login -> viewModelScope.launch {
                _state.update { it.copy(isLoading = true) }

                val loginResult = loginUseCase(intent.username, intent.password)
                if (loginResult.isSuccess) {
                    _state.update {
                        LoginState(
                            isLoading = false,
                            isLoggedIn = true,
                            errorMessage = null
                        )
                    }
                } else {
                    _state.update {
                        LoginState(
                            isLoading = false,
                            isLoggedIn = false,
                            errorMessage = loginResult.exceptionOrNull()?.message
                        )
                    }
                }
            }
        }
    }
}