package com.stebitto.feature_login.impl

import androidx.lifecycle.ViewModel
import com.stebitto.common.api.MVIViewModel
import com.stebitto.common.api.UserRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

internal class LoginViewModel(
    private val userRepository: UserRepository,
    initialState: LoginState = LoginState()
) : MVIViewModel<LoginState, LoginIntent, LoginEffect>, ViewModel() {

    private val _state = MutableStateFlow(initialState)
    override val state: StateFlow<LoginState>
        get() = _state.asStateFlow()

    private val _sideEffects = Channel<LoginEffect>()
    override val sideEffects: Flow<LoginEffect>
        get() = _sideEffects.receiveAsFlow()

    override fun dispatch(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.LoginButtonClicked -> _state.update {
                it.copy(isLoading = true, errorMessage = null)
            }
            is LoginIntent.LoginSuccess -> {
                userRepository.saveGithubToken(intent.accessToken)
                _sideEffects.trySend(LoginEffect.LoginSuccess)
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