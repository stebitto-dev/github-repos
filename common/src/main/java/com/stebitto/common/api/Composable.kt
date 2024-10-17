package com.stebitto.common.api

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.stebitto.common.R

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
fun AppTopBar(
    showNavigateBack: Boolean,
    showSignOut: Boolean,
    repoName: String? = null,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onNavigateBack: () -> Unit = {},
    onSignOut: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            if (repoName != null) {
                with(sharedTransitionScope) {
                    Text(
                        text = repoName,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.sharedElement(
                            state = rememberSharedContentState(key = repoName),
                            animatedVisibilityScope = animatedContentScope
                        )
                    )
                }
            } else {
                Text(
                    text = stringResource(R.string.app_name),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        navigationIcon = {
            if (showNavigateBack) {
                IconButton(onClick = { onNavigateBack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigate back"
                    )
                }
            }
        },
        actions = {
            with(sharedTransitionScope) {
                Image(
                    painter = painterResource(id = R.drawable.github_logo),
                    contentDescription = stringResource(R.string.github_logo_content_description),
                    modifier = Modifier
                        .sharedElement(
                            state = rememberSharedContentState(key = R.drawable.github_logo),
                            animatedVisibilityScope = animatedContentScope
                        )
                        .size(24.dp),
                    contentScale = ContentScale.Fit
                )
            }

            if (showSignOut) {
                IconButton(onClick = { onSignOut() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = "Logout"
                    )
                }
            }
        }
    )
}

fun Modifier.customShadow(
    color: Color,
    blurRadius: Dp,
    spread: Dp
) = this.then(
    Modifier.drawBehind {
        drawIntoCanvas { canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.color = color.copy(alpha = 0.7f).toArgb()
            frameworkPaint.setShadowLayer(
                blurRadius.toPx(),
                spread.toPx(),
                spread.toPx(),
                color.toArgb()
            )
            canvas.save()
            canvas.drawRoundRect(
                0f,
                0f,
                size.width,
                size.height,
                20.dp.toPx(),
                20.dp.toPx(),
                paint
            )
            canvas.restore()
        }
    }
)