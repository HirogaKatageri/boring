package dev.hirogakatageri.boring.user.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.hirogakatageri.boring.user.model.User
import dev.hirogakatageri.boring.user.viewmodel.UserViewModel

@Composable
fun UserDetailScreen(
    viewModel: UserViewModel,
    userId: Int,
    onNavigateUp: () -> Unit
) {
    // Load the user when the screen is first displayed
    LaunchedEffect(userId) {
        viewModel.selectUser(userId)
    }
    
    // Observe the selected user
    val selectedUser by viewModel.selectedUser.collectAsState()
    
    Box(modifier = Modifier.fillMaxSize()) {
        if (selectedUser == null) {
            // Show loading indicator while user is being loaded
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            // If user is null after loading, navigate back
            selectedUser?.let { user ->
                UserDetailContent(
                    user = user,
                    onNavigateUp = onNavigateUp
                )
            } ?: LaunchedEffect(Unit) {
                onNavigateUp()
            }
        }
    }
}

@Composable
fun UserDetailContent(
    user: User,
    onNavigateUp: () -> Unit
) {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        // User avatar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = user.getDisplayImage(),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        
        // Full name
        Text(
            text = "${user.firstName} ${user.lastName}",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Personal Information Section
        SectionHeader("Personal Information")
        
        InfoRow("Username", user.username)
        InfoRow("Age", user.age.toString())
        InfoRow("Gender", user.gender)
        user.birthDate?.let { InfoRow("Birth Date", it) }
        user.maidenName?.let { InfoRow("Maiden Name", it) }
        user.height?.let { InfoRow("Height", "$it cm") }
        user.weight?.let { InfoRow("Weight", "$it kg") }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Contact Information Section
        SectionHeader("Contact Information")
        
        InfoRow("Email", user.email)
        InfoRow("Phone", user.phone)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Education Section
        user.university?.let {
            SectionHeader("Education")
            InfoRow("University", it)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Divider(modifier = Modifier.padding(bottom = 8.dp))
}

@Composable
private fun InfoRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}