package dev.hirogakatageri.boring.user.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import dev.hirogakatageri.boring.user.view.UserDetailScreen
import dev.hirogakatageri.boring.BoringTheme
import dev.hirogakatageri.boring.user.viewmodel.UserViewModel
import org.koin.androidx.compose.koinViewModel

class UserDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the user ID from the intent
        val userId = intent.getIntExtra("USER_ID", -1)

        setContent {
            UserDetailsScreen(userId)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailsScreen(userId: Int) {
    BoringTheme {
        // Get ViewModel from Koin
        val viewModel: UserViewModel = koinViewModel()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Boring App - User Details") }
                )
            }
        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                val context = LocalContext.current
                UserDetailScreen(
                    viewModel = viewModel,
                    userId = userId,
                    onNavigateUp = { /* Finish the activity when navigating up */
                        (context as? ComponentActivity)?.finish()
                    }
                )
            }
        }
    }
}
