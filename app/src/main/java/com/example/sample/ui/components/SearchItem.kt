package com.example.sample.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sample.data.local.model.RecentSearch

@Composable
fun SearchItem(
    recentSearch: RecentSearch,
    onClickedDeleteSearch: (searchId: Int) -> Unit
) {
    Row(
        modifier = Modifier.padding(0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = recentSearch.content,
            color = Color.Black,
            style = TextStyle(textDecoration = TextDecoration.None)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "delete",
            modifier = Modifier
                .size(32.dp)
                .clickable {
                   onClickedDeleteSearch(recentSearch.uid)
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchItem() {
    SearchItem(
        recentSearch = RecentSearch(content = "test"),
        onClickedDeleteSearch = {}
    )
}