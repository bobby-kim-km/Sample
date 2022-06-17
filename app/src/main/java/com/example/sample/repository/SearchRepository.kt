package com.example.sample.repository

import com.example.sample.data.JobResult
import com.example.sample.data.local.model.RecentSearch
import com.example.sample.data.local.source.RecentSearchDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val recentSearchDao: RecentSearchDao,
) {

    suspend fun getAllRecentSearch(): JobResult<List<RecentSearch>> {
        return withContext(Dispatchers.IO) {
            try {
                val recentSearches = recentSearchDao.getAll()
                JobResult.Success(recentSearches)
            } catch (e: Exception) {
                JobResult.Error(e)
            }
        }
    }

    suspend fun getRecentSearch(searchId: Int): RecentSearch {
        return recentSearchDao.get(searchId)
    }

    suspend fun insertRecentSearch(recentSearch: RecentSearch) {
        return recentSearchDao.insert(recentSearch)
    }

    suspend fun deleteRecentSearch(searchId: Int) {
        val search: RecentSearch = getRecentSearch(searchId)
        return recentSearchDao.delete(search)
    }

    suspend fun deleteAllRecentSearch() {
        return recentSearchDao.deleteAll()
    }



}