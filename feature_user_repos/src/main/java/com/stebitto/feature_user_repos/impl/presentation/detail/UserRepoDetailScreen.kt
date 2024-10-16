package com.stebitto.feature_user_repos.impl.presentation.detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stebitto.common.api.theme.MyApplicationTheme
import com.stebitto.feature_user_repos.R
import com.stebitto.feature_user_repos.impl.models.UserRepoDetailPresentation
import org.koin.androidx.compose.koinViewModel

internal const val TEST_REPO_DETAIL_ERROR_MESSAGE = "TEST_REPO_DETAIL_ERROR_MESSAGE"
internal const val TEST_REPO_DETAIL_LOADING_INDICATOR = "TEST_REPO_DETAIL_LOADING_INDICATOR"
internal const val TEST_REPO_DETAIL_NO_REPO_FOUND = "TEST_REPO_DETAIL_NO_REPO_FOUND"
internal const val TEST_REPO_DETAIL = "TEST_REPO_DETAIL"

@Composable
internal fun UserRepoDetailScreen(
    viewModel: UserRepoDetailViewModel = koinViewModel(),
    owner: String,
    repoName: String
) {
    val uiState = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.dispatch(UserRepoDetailIntent.LoadUserRepo(owner, repoName))
    }

    RepositoryDetailScreen(
        repo = uiState.value.userRepo,
        isLoading = uiState.value.isLoading,
        errorMessage = uiState.value.errorMessage,
        onStarClick = { isStarred ->
            viewModel.dispatch(UserRepoDetailIntent.StarClicked(owner, repoName, isStarred))
        }
    )
}

@Composable
internal fun RepositoryDetailScreen(
    repo: UserRepoDetailPresentation?,
    isLoading: Boolean,
    errorMessage: String?,
    onStarClick: (isStarred: Boolean) -> Unit = {}
) {
    when {
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.testTag(
                    TEST_REPO_DETAIL_LOADING_INDICATOR
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
                    modifier = Modifier
                        .padding(16.dp)
                        .testTag(TEST_REPO_DETAIL_ERROR_MESSAGE)
                )
            }
        }
        repo == null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(R.string.no_repository_found),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(16.dp)
                        .testTag(TEST_REPO_DETAIL_NO_REPO_FOUND)
                )
            }
        }
        else -> {
            RepositoryDetail(repo, onStarClick)
        }
    }
}

@Composable
internal fun RepositoryDetail(
    repo: UserRepoDetailPresentation,
    onStarClick: (isStarred: Boolean) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag(TEST_REPO_DETAIL)
    ) {
        Text(
            text = repo.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = stringResource(R.string.owner_label, repo.owner))
        Text(text = stringResource(R.string.description_label, repo.description))
        Text(text = stringResource(R.string.language_label, repo.language))
        Text(text = stringResource(R.string.private_label, if (repo.private) "Yes" else "No"))
        Text(text = stringResource(R.string.html_url_label, repo.htmlUrl))

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = { onStarClick(repo.isStarred) },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Stars",
                    tint = if (repo.isStarred) Color.Yellow else Color.Gray
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "${repo.numberOfStars}")

            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = "Issues",
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "${repo.numberOfIssues}")

            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                imageVector = Icons.Filled.Face,
                contentDescription = "Watchers",
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "${repo.numberOfWatchers}")
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
internal fun RepositoryDetailScreenPreview() {
    MyApplicationTheme {
        RepositoryDetailScreen(
            repo = null,
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
internal fun RepositoryDetailPreview() {
    MyApplicationTheme {
        RepositoryDetail(
            repo = UserRepoDetailPresentation(
                id = 1,
                name = "My first repository",
                owner = "octocat",
                description = "This is my first repository",
                language = "Kotlin",
                private = false,
                htmlUrl = "https://github.com/octocat/my-first-repository",
                numberOfStars = 100,
                numberOfWatchers = 50,
                numberOfIssues = 20,
                isStarred = false
            )
        )
    }
}