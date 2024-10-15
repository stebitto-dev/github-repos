package com.stebitto.feature_user_repos.impl.presentation.list

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stebitto.common.api.theme.MyApplicationTheme
import com.stebitto.feature_user_repos.R
import com.stebitto.feature_user_repos.impl.models.UserRepoPresentation
import org.koin.androidx.compose.koinViewModel

internal const val TEST_REPO_LIST_ERROR_MESSAGE = "TEST_REPO_LIST_ERROR_MESSAGE"
internal const val TEST_REPO_LIST_LOADING_INDICATOR = "TEST_REPO_LIST_LOADING_INDICATOR"
internal const val TEST_REPO_LIST_COLUMN = "TEST_REPO_LIST_COLUMN"

@Composable
internal fun UserRepoScreen(
    viewModel: UserRepoViewModel = koinViewModel(),
    onRepoClick: (id: Int) -> Unit = {}
) {
    val uiState = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.dispatch(UserRepoIntent.FetchRepos)
    }

    UserRepoList(
        repos = uiState.value.repos,
        isLoading = uiState.value.isLoading,
        errorMessage = uiState.value.errorMessage,
        onRepoClick = onRepoClick
    )
}

@Composable
internal fun UserRepoList(
    repos: List<UserRepoPresentation>,
    isLoading: Boolean,
    errorMessage: String?,
    onRepoClick: (id: Int) -> Unit = {}
) {
    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.testTag(
                    TEST_REPO_LIST_LOADING_INDICATOR
                ))
            }
        }
        errorMessage != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp).testTag(TEST_REPO_LIST_ERROR_MESSAGE)
                )
            }
        }
        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .testTag(TEST_REPO_LIST_COLUMN)
            ) {
                items(repos, key = { it.id }) { repo ->
                    RepositoryCard(repo, onItemClick = onRepoClick)
                }
            }
        }
    }
}

@Composable
internal fun RepositoryCard(
    repository: UserRepoPresentation,
    onItemClick: (id: Int) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(repository.id) },
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = repository.name.ifBlank { stringResource(R.string.no_name_available) },
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = repository.description.ifBlank { stringResource(R.string.no_description_available) },
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text("Language: ${repository.language.ifBlank { stringResource(R.string.unknown_language) }}")
                Spacer(modifier = Modifier.weight(1f))
                Text("Stars: ${repository.numberOfStars}")
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
internal fun UserRepoListPreview() {
    MyApplicationTheme {
        val repository = UserRepoPresentation(
            id = 1,
            name = "My first repository",
            description = "This is my first repository",
            language = "Kotlin",
            numberOfStars = 100
        )
        UserRepoList(
            repos = listOf(
                repository,
                repository.copy(id = 2, name = "My second repository"),
                repository.copy(id = 3, name = "My third repository")
            ),
            isLoading = false,
            errorMessage = null
        )
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
internal fun RepositoryCardPreview() {
    MyApplicationTheme {
        RepositoryCard(
            repository = UserRepoPresentation(
                id = 1,
                name = "My first repository",
                description = "This is my first repository",
                language = "Kotlin",
                numberOfStars = 100
            )
        )
    }
}