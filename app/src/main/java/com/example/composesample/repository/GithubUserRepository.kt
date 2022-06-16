package com.example.composesample.repository

import com.example.composesample.data.JobResult
import com.example.composesample.data.remote.dto.UserRequestDto
import com.example.composesample.data.remote.dto.UserResponseDto
import com.example.composesample.data.remote.source.GithubUserRemoteSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubUserRepository @Inject constructor(
    private val githubUserRemoteSource: GithubUserRemoteSource
) {

    suspend fun getUser(userRequestDto: UserRequestDto): JobResult<UserResponseDto> {
        return githubUserRemoteSource.getUser(userRequestDto)
    }

}
