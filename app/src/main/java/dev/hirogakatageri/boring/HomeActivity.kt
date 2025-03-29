package dev.hirogakatageri.boring

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
import dev.hirogakatageri.boring.user.view.UserScreen
import dev.hirogakatageri.boring.user.viewmodel.UserViewModel
import org.koin.androidx.compose.koinViewModel

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HomeScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    BoringTheme {
        // Get ViewModel from Koin
        val viewModel: UserViewModel = koinViewModel()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Boring App - Users") }
                )
            }
        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background
            ) {
                UserScreen(viewModel = viewModel)
            }
        }
    }
}
