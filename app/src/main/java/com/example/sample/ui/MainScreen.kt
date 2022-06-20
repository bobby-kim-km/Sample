package com.example.sample.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.sample.data.local.model.RecentSearch
import com.example.sample.data.remote.dto.UserResponseDto
import com.example.sample.ui.components.SearchItem
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
        getUserInfo = { viewModel.getUserInfo(uiState.githubIdTextFieldValue) },
        setGithubIdTextField = { inputStr -> viewModel.setGithubIdTextField(inputStr) },
        onClickedDeleteSearch = { searchId -> viewModel.deleteRecentSearch(searchId) }
    )

}

@Composable
private fun MainScreen(
    uiState: MainUiState,
    openUserGithubPage: (url: String) -> Unit,
    getUserInfo: () -> Unit,
    setGithubIdTextField: (inputStr: String) -> Unit,
    onClickedDeleteSearch: (searchId: Int) -> Unit
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
            RecentSearchScreen(
                recentSearchList = uiState.recentSearchList,
                onClickedDeleteSearch = onClickedDeleteSearch
            )
            Spacer(modifier = Modifier.height(8.dp))

            when(getScreenType(uiState)) {
                ScreenType.HasUserData -> {
                    check(uiState is MainUiState.HasUserData)
                    SearchSuccessScreen(
                        user = uiState.user,
                        openUserGithubPage = openUserGithubPage
                    )
                }
                ScreenType.NoUserData -> {
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


@Composable
private fun RecentSearchScreen(
    recentSearchList: List<RecentSearch>,
    onClickedDeleteSearch: (searchId: Int) -> Unit
) {
    Column (
        modifier = Modifier.padding(8.dp)
    ) {
        Text("[Recent Search List]")
        Spacer(modifier = Modifier.height(4.dp))
        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp, horizontal = 16.dp),   // 상하 좌우 패딩
            verticalArrangement = Arrangement.spacedBy(16.dp),  // 아이템간의 패딩
        ) {
            items(recentSearchList) { item ->

                Column(
                    Modifier.padding(2.dp)
                ) {
                    SearchItem(
                        recentSearch = item,
                        onClickedDeleteSearch = onClickedDeleteSearch
                    )
                }

            }
        }
    }
}

private enum class ScreenType {
    NoUserData,
    Error,
    HasUserData
}

@Composable
private fun getScreenType(
    uiState: MainUiState
): ScreenType = when (uiState) {
    is MainUiState.HasUserData -> {
        ScreenType.HasUserData
    }
    is MainUiState.NoUserData -> {
        if (uiState.errorMessage == "") {
            ScreenType.NoUserData
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

@Preview(showBackground = true)
@Composable
private fun PreviewRecentSearchScreen() {
    val recentSearchList = arrayListOf(
        RecentSearch(content = "search 1").apply { uid = 1 },
        RecentSearch(content = "search 2").apply { uid = 2 },
        RecentSearch(content = "search 3").apply { uid = 3 },
        RecentSearch(content = "search 4").apply { uid = 4 },
        RecentSearch(content = "search 5").apply { uid = 5 }
    )
    RecentSearchScreen(
        recentSearchList = recentSearchList,
        onClickedDeleteSearch = {}
    )
}