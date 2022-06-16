package com.example.sample.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.sample.data.remote.dto.UserResponseDto
import com.example.sample.view_model.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    openUserGithubPage: (url: String) -> Unit
) {

    val uiState: MainUiState by viewModel.uiState.collectAsState()
    MainScreen(
        uiState = uiState,
        openUserGithubPage = openUserGithubPage,
        getUserInfo = { viewModel.getUserInfo(uiState.githubIdTextFieldValue)},
        setGithubIdTextField = { inputStr -> viewModel.setGithubIdTextField(inputStr) }
    )

}

@Composable
private fun MainScreen(
    uiState: MainUiState,
    openUserGithubPage: (url: String) -> Unit,
    getUserInfo: () -> Unit,
    setGithubIdTextField: (inputStr: String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sample") }
            )
        },
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SearchBarScreen(
                textFieldValue = uiState.githubIdTextFieldValue,
                getUserInfo = getUserInfo,
                setGithubIdTextField = setGithubIdTextField
            )

            Spacer(modifier = Modifier.height(8.dp))

            when(getScreenType(uiState)) {
                ScreenType.HasData -> {
                    check(uiState is MainUiState.HasData)
                    SearchSuccessScreen(
                        user = uiState.user,
                        openUserGithubPage = openUserGithubPage
                    )
                }
                ScreenType.NoData -> {
                    Text("No Data")
                }
                ScreenType.Error -> {
                    Text("[ERROR]")
                    Spacer(modifier = Modifier.height(8.dp))
                    val errorMessage = uiState.errorMessage
                    Text(errorMessage)
                }
            }

        }
    }
}

@Composable
private fun SearchBarScreen(
    textFieldValue: String,
    getUserInfo: () -> Unit,
    setGithubIdTextField: (inputStr: String) -> Unit
) {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { setGithubIdTextField(it) },
            label = { Text(text = "github id") },
            maxLines = 1,
            textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Normal)
        )
        Spacer(modifier = Modifier.width(16.dp))
        IconButton(
            onClick = getUserInfo
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "search",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
private fun SearchSuccessScreen(
    user: UserResponseDto,
    openUserGithubPage: (url: String) -> Unit
) {
    Column (
        modifier = Modifier.padding(8.dp)
    ) {
        Text("[RESULT]")
        Spacer(modifier = Modifier.height(8.dp))
        val msg: String = "id : ${user.login}\n" +
                "name : ${user.name}\n"
        Text(text = msg)

        Row {
            Image(
                painter = rememberAsyncImagePainter(model = user.avatarUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            ClickableText(
                text = AnnotatedString(user.htmlUrl),
                onClick = {
                    openUserGithubPage(user.htmlUrl)
                },
                style = TextStyle(color = Color.Blue)
            )
        }
    }
}

private enum class ScreenType {
    NoData,
    Error,
    HasData
}

@Composable
private fun getScreenType(
    uiState: MainUiState
): ScreenType = when (uiState) {
    is MainUiState.HasData -> {
        ScreenType.HasData
    }
    is MainUiState.NoData -> {
        if (uiState.errorMessage == "") {
            ScreenType.NoData
        } else {
            ScreenType.Error
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewSearchBarScreen() {
    SearchBarScreen(
        textFieldValue = "test",
        getUserInfo = { },
        setGithubIdTextField = { }
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearchSuccessScreen() {
    val user = UserResponseDto(
        id = 0, login = "bobby-kim-km", name = "Bobby", avatarUrl = "https://avatars.githubusercontent.com/u/86952925?s=96&v=4", htmlUrl = "https://github.com/bobby-kim-km"
    )
    SearchSuccessScreen(
        user = user,
        openUserGithubPage = { }
    )
}