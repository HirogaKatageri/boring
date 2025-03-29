package dev.hirogakatageri.boring.user.view

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.hirogakatageri.boring.user.activity.UserDetailsActivity
import dev.hirogakatageri.boring.user.model.User
import dev.hirogakatageri.boring.user.viewmodel.UserViewModel

@Composable
fun UserScreen(
    viewModel: UserViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is UserViewModel.UserUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is UserViewModel.UserUiState.Success -> {
                val successState = uiState as UserViewModel.UserUiState.Success
                UserList(
                    users = successState.users,
                    hasMoreData = successState.hasMoreData,
                    onLoadMore = { viewModel.loadUsers() }
                )
            }
            is UserViewModel.UserUiState.Error -> {
                val errorMessage = (uiState as UserViewModel.UserUiState.Error).message
                ErrorView(
                    message = errorMessage,
                    onRetry = { viewModel.refreshUsers() }
                )
            }
        }
    }
}

@Composable
fun UserList(
    users: List<User>,
    hasMoreData: Boolean,
    onLoadMore: () -> Unit
) {
    val listState = rememberLazyListState()
    val context = LocalContext.current

    // Detect when we're close to the end of the list to load more data
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            val lastVisibleIndex = lastVisibleItem?.index ?: 0
            val totalCount = listState.layoutInfo.totalItemsCount
            val bufferZone = totalCount - 3;
            lastVisibleIndex >= bufferZone && hasMoreData
        }
    }

    // Load more data when we're close to the end of the list
    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            onLoadMore()
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(users) { index, user ->
            UserItem(
                user = user, 
                index = index + 1,
                onClick = {
                    // Launch UserDetailsActivity with the user ID
                    val intent = Intent(context, UserDetailsActivity::class.java).apply {
                        putExtra("USER_ID", user.id)
                    }
                    context.startActivity(intent)
                }
            )

            if (index < users.size - 1) {
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                )
            }
        }

        // Only show progress indicator if there's more data to load and we're not at the end of the list
        if (hasMoreData) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                }
            }
        }
    }
}

@Composable
fun UserItem(
    user: User,
    index: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Index number
            Text(
                text = "$index.",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 8.dp)
            )

            // User avatar
            AsyncImage(
                model = user.getDisplayImage(),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // User details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Name
                Text(
                    text = "${user.firstName} ${user.lastName}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Age
                Text(
                    text = "Age: ${user.age}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Email
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Phone
                Text(
                    text = user.phone,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun ErrorView(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = onRetry
        ) {
            Text("Retry")
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}
