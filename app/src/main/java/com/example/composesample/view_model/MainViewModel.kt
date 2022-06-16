package com.example.composesample.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesample.data.JobResult
import com.example.composesample.data.remote.dto.UserRequestDto
import com.example.composesample.data.remote.dto.UserResponseDto
import com.example.composesample.data.succeeded
import com.example.composesample.repository.GithubUserRepository
import com.example.composesample.ui.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val githubUserRepository: GithubUserRepository
): ViewModel() {

    private val viewModelState = MutableStateFlow(
        MainState(isLoading = true)
    )

    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        val initGithubId = "bobby-kim-km"
        setGithubIdTextField(initGithubId)
        getUserInfo(initGithubId)
    }

    fun getUserInfo(userId: String) = viewModelScope.launch {
        viewModelState.update { it.copy(isLoading = true) }
        val reqUser = UserRequestDto(userId)
        val getUserDeferred: Deferred<JobResult<UserResponseDto>> = async { githubUserRepository.getUser(reqUser) }
        val getUserResult = getUserDeferred.await()
        if (getUserResult.succeeded) {
            getUserResult as JobResult.Success
            viewModelState.update {
                it.copy(user = getUserResult.data, isLoading = false)
            }
        } else {
            getUserResult as JobResult.Error
            viewModelState.update {
                it.copy(user = null, errorMessage = getUserResult.exception.message, isLoading = false)
            }
        }
    }

    fun setGithubIdTextField(inputStr: String) {
        viewModelState.update { it.copy(githubIdTextFieldValue = inputStr) }
    }

}