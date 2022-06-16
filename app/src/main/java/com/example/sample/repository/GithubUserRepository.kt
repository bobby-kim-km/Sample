package com.example.sample.repository

import com.example.sample.data.JobResult
import com.example.sample.data.remote.dto.UserRequestDto
import com.example.sample.data.remote.dto.UserResponseDto
import com.example.sample.data.remote.source.GithubUserRemoteSource
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
