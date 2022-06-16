package com.example.sample.data.remote.source

import com.example.sample.data.JobResult
import com.example.sample.data.remote.api.GithubUserService
import com.example.sample.data.remote.dto.UserRequestDto
import com.example.sample.data.remote.dto.UserResponseDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubUserRemoteSource @Inject constructor(
    private val githubUserService: GithubUserService
) {

    suspend fun getUser(userRequestDto: UserRequestDto): JobResult<UserResponseDto> {
        return try {
            val response: UserResponseDto = githubUserService.getUser(userRequestDto.id)
            JobResult.Success(response)
        } catch(e: Exception) {
            JobResult.Error(e)
        }
    }

}