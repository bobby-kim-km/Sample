package com.example.sample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sample.ui.MainScreen
import com.example.sample.ui.theme.SampleTheme
import com.example.sample.view_model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: MainViewModel = viewModel()

            SampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(
                        viewModel = viewModel,
                        openUserGithubPage = { url ->
                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse(url)
                            }
                            startActivity(intent)
                        }
                    )
                }

            }
        }
    }
}