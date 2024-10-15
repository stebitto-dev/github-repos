package com.stebitto.feature_user_repos.impl.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stebitto.feature_user_repos.impl.models.UserRepoDetailPresentation
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun UserRepoDetailScreen(
    viewModel: UserRepoDetailViewModel = koinViewModel(),
    repoId: Int
) {
    val uiState = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.dispatch(UserRepoDetailIntent.LoadUserRepo(repoId))
    }

    RepositoryDetailScreen(repo = uiState.value.userRepo)
}

@Composable
internal fun RepositoryDetailScreen(
    repo: UserRepoDetailPresentation?
) {
    if (repo == null) {
        Text(text = "Loading...")
    } else {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(text = "Name:", style = MaterialTheme.typography.headlineSmall)
            Text(text = repo.name.ifBlank { "No name available" }, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Description:", style = MaterialTheme.typography.headlineSmall)
            Text(text = repo.description.ifBlank { "No description available" }, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Language:", style = MaterialTheme.typography.headlineSmall)
            Text(text = repo.language.ifBlank { "No language available" }, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Stars:", style = MaterialTheme.typography.headlineSmall)
            Text(text = repo.numberOfStars.toString(), style = MaterialTheme.typography.bodyMedium)
        }
    }
}