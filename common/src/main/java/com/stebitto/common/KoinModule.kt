package com.stebitto.common

import com.stebitto.common.api.GetGithubTokenUseCase
import com.stebitto.common.api.SaveGithubTokenUseCase
import com.stebitto.common.impl.GetGithubTokenUseCaseImpl
import com.stebitto.common.impl.SaveGithubTokenUseCaseImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val commonModule = module {
    factory<SaveGithubTokenUseCase> { SaveGithubTokenUseCaseImpl(androidContext()) }
    factory<GetGithubTokenUseCase> { GetGithubTokenUseCaseImpl(androidContext()) }
}