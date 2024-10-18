# Github Repos

This app let user login with his Github account and browse his repositories.

<img src="https://drive.google.com/uc?export=view&id=1Mn27H1JLrGAd0hDgzKtFyZxspOpk6Gsu" width="250" />

From the list, the user is able to see a detail screen with a dedicated button to star/unstar the repo.

<img src="https://drive.google.com/uc?export=view&id=1rvhIOOgzyRgYk6x7JinCdESR3311TmpN" width="250" /> <img src="https://drive.google.com/uc?export=view&id=1yREN6AIgja8PVYmPQKl09VPrFM9wXk6P" width="250" />

Additionally, in the Top Bar there is a logout button.


## Technical details

The project is divided into following modules:

- *app*: main module
- *common*: module for common resources, shared throughout the project
- *feature_login*: module for login functionalities
- *feature_user_repos*: module for list and detail functionalities

Architecture is following MVI pattern.

Firebase is leveraged in order to login with Github credentials, via Firebase Authentication. User access token is stored in a SharedPreferences file, which is not the most secure way to do it. For this reason, I created a dedicated repository class (*common/api/UserRepository.kt*), so that is easy to change approach using Android Keystore for example.

Moving into the fetch repositories feature, data from web service are stored in a local database to support offline mode.

CI sample can be found in *.github/workflows/ci.yml*.

Libraries used are as follows:

 - [Jetpack Compose](https://developer.android.com/jetpack/compose) for UI
 - [Firebase Auth](https://firebase.google.com/docs/auth), an authentication framework for serverless apps
 - [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) and [Kotlin Flow](https://kotlinlang.org/docs/flow.html) for async tasks
 - [Retrofit](https://square.github.io/retrofit/), a type-safe HTTP client
 - [Room DB](https://developer.android.com/training/data-storage/room) as local database
 - [Koin](https://insert-koin.io/) as dependency injection framework
 - [Mockito](https://site.mockito.org/), a mocking framework for unit tests
 - [Turbine](https://github.com/cashapp/turbine), a testing library for Kotlin Flow

## Bonus

There are a couple of features to highlight:

 1. *custom shadow modifier*: in *common/api/Composable.kt* there is a Modifier extension called **customShadow** which implements a custom shadow for Composables, taking color, blurRadius and spread as paramters. This extension is used in the repository list screen.
 2. *animated elements*: there are a few elements animated via **SharedTransitionLayout** during transition between screens

## Additional info

 - Android SDK target: **34 (Android 14.0)**
 - minimum Android SDK supported: **23 (Android 6)**
 - Kotlin version: **2.0.20**
 - Android Gradle plugin version: **8.7.0**
