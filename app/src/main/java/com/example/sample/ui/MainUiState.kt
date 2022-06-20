package com.example.sample.ui

import com.example.sample.data.local.model.RecentSearch
import com.example.sample.data.remote.dto.UserResponseDto

data class MainViewModelState(
    val user: UserResponseDto? = null,
    val githubIdTextFieldValue: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val recentSearchList: List<RecentSearch>? = null
) {

    fun toUiState(): MainUiState =
        if (user == null) {
            MainUiState.NoData(
                isLoading = isLoading,
                errorMessage = errorMessage ?: "",
                githubIdTextFieldValue = githubIdTextFieldValue,
                recentSearchList = recentSearchList ?: arrayListOf()
            )
        } else {
            MainUiState.HasData(
                user = user,
                isLoading = isLoading,
                errorMessage = errorMessage ?: "",
                githubIdTextFieldValue = githubIdTextFieldValue,
                recentSearchList = recentSearchList ?: arrayListOf()
            )
        }

}


sealed interface MainUiState {
    val isLoading: Boolean
    val errorMessage: String
    val githubIdTextFieldValue: String
    val recentSearchList: List<RecentSearch>

    data class NoData(
        override val isLoading: Boolean,
        override val errorMessage: String,
        override val githubIdTextFieldValue: String,
        override val recentSearchList: List<RecentSearch>,
    ): MainUiState

    data class HasData(
        val user: UserResponseDto,
        override val isLoading: Boolean,
        override val errorMessage: String,
        override val githubIdTextFieldValue: String,
        override val recentSearchList: List<RecentSearch>,
    ): MainUiState

}