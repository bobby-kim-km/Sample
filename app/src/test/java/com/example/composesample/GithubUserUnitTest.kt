package com.example.composesample

import com.example.composesample.data.JobResult
import com.example.composesample.data.remote.api.GithubUserService
import com.example.composesample.data.remote.dto.UserRequestDto
import com.example.composesample.data.remote.source.GithubUserRemoteSource
import com.example.composesample.data.succeeded
import com.example.composesample.repository.GithubUserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GithubUserUnitTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun gitHubUserApiTest() = runTest {
//        val gitHubId = "yeon1216"
//        val userRequest = UserRequestDto(id = gitHubId)
//        val service: GithubUserService = GithubUserService.create()
//        val githubUserRemoteSource = GithubUserRemoteSource(service)
//        val githubUserRepository = GithubUserRepository(githubUserRemoteSource)
//        val result = githubUserRepository.getUser(userRequest)
//        println("result : $result")
//        MatcherAssert.assertThat(result.succeeded, CoreMatchers.`is`(true))
//        result as JobResult.Success
//        println("userId : ${result.data.login}")
    }

}