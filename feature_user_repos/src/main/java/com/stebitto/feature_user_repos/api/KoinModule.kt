package com.stebitto.feature_user_repos.api

import com.stebitto.feature_user_repos.impl.data.GitHubService
import com.stebitto.feature_user_repos.impl.data.GithubRemoteSource
import com.stebitto.feature_user_repos.impl.data.GithubRemoteSourceImpl
import com.stebitto.feature_user_repos.impl.data.GithubRepositoryImpl
import com.stebitto.feature_user_repos.impl.data.GithubUserRepoUseCaseImpl
import com.stebitto.feature_user_repos.impl.presentation.UserRepoViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideHttpClient() }
    single { provideConverterFactory() }
    single { provideRetrofit(get(), get()) }
    single { provideService(get()) }
}

val featureUserReposModule = module {
    viewModel { UserRepoViewModel(get()) }
    factory<GithubUserRepoUseCase> { GithubUserRepoUseCaseImpl(get(), get()) }
    factory<GithubRemoteSource> { GithubRemoteSourceImpl(get()) }
    single <GithubRepository> { GithubRepositoryImpl(get()) }
    includes(networkModule)
}

internal fun provideHttpClient(): OkHttpClient {
    return OkHttpClient
        .Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(
            HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
        )
        .build()
}

internal fun provideConverterFactory(): GsonConverterFactory =
    GsonConverterFactory.create()

internal fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()
}

internal fun provideService(retrofit: Retrofit): GitHubService =
    retrofit.create(GitHubService::class.java)