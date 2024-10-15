package com.stebitto.feature_user_repos.api

import android.app.Application
import androidx.room.Room
import com.stebitto.common.api.AuthorizationInterceptor
import com.stebitto.feature_user_repos.impl.data.usecases.GetGithubUserRepoListUseCase
import com.stebitto.feature_user_repos.impl.data.retrofit.GitHubService
import com.stebitto.feature_user_repos.impl.data.GithubRemoteSource
import com.stebitto.feature_user_repos.impl.data.GithubRemoteSourceImpl
import com.stebitto.feature_user_repos.impl.data.GithubRepositoryImpl
import com.stebitto.feature_user_repos.impl.data.usecases.GetGithubUserRepoListUseCaseImpl
import com.stebitto.feature_user_repos.impl.data.GithubLocalSource
import com.stebitto.feature_user_repos.impl.data.GithubLocalSourceImpl
import com.stebitto.feature_user_repos.impl.data.room.AppDatabase
import com.stebitto.feature_user_repos.impl.presentation.detail.UserRepoDetailViewModel
import com.stebitto.feature_user_repos.impl.presentation.list.UserRepoViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideHttpClient(AuthorizationInterceptor(get())) }
    single { provideConverterFactory() }
    single { provideRetrofit(get(), get()) }
    single { provideService(get()) }
}

val localDbModule = module {
    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

val featureUserReposModule = module {
    viewModel { UserRepoViewModel(get()) }
    viewModel { UserRepoDetailViewModel(get()) }
    factory<GetGithubUserRepoListUseCase> { GetGithubUserRepoListUseCaseImpl(get()) }
    factory<GithubRemoteSource> { GithubRemoteSourceImpl(get()) }
    factory<GithubLocalSource> { GithubLocalSourceImpl(get()) }
    single<GithubRepository> { GithubRepositoryImpl(get(), get()) }
    includes(networkModule)
    includes(localDbModule)
}

internal fun provideHttpClient(
    authorizationInterceptor: AuthorizationInterceptor
): OkHttpClient {
    return OkHttpClient
        .Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(
            HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
        )
        .addInterceptor(authorizationInterceptor)
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

internal fun provideDatabase(application: Application): AppDatabase {
    return Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "app_database"
    ).build()
}

internal fun provideDao(database: AppDatabase) = database.userRepoDao()