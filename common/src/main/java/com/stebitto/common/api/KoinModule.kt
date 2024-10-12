package com.stebitto.common.api

import com.stebitto.common.impl.GithubRepositoryImpl
import org.koin.dsl.module

val commonModule = module {
    single<GithubRepository> { GithubRepositoryImpl() }
}