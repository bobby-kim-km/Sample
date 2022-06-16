package com.example.sample.ui

import com.example.sample.data.remote.dto.UserResponseDto

data class MainState(
    val user: UserResponseDto? = null,
    val githubIdTextFieldValue: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
) {

    fun toUiState(): MainUiState =
        if (user == null) {
            MainUiState.NoData(
                isLoading = isLoading,
                errorMessage = errorMessage ?: "",
                githubIdTextFieldValue = githubIdTextFieldValue
            )
        } else {
            MainUiState.HasData(
                user = user,
                isLoading = isLoading,
                errorMessage = errorMessage ?: "",
                githubIdTextFieldValue = githubIdTextFieldValue
            )
        }

}


sealed interface MainUiState {
    val isLoading: Boolean
    val errorMessage: String
    val githubIdTextFieldValue: String

    data class NoData(
        override val isLoading: Boolean,
        override val errorMessage: String,
        override val githubIdTextFieldValue: String,
    ): MainUiState

    data class HasData(
        val user: UserResponseDto,
        override val isLoading: Boolean,
        override val errorMessage: String,
        override val githubIdTextFieldValue: String,
    ): MainUiState

}