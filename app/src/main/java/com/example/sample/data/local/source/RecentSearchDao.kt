package com.example.sample.data.local.source

import androidx.room.*
import com.example.sample.data.local.model.RecentSearch
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchDao {

    @Query("SELECT * FROM RecentSearch ORDER BY date DESC")
    fun observeNotes(): Flow<List<RecentSearch>>

    @Query("SELECT * FROM RecentSearch ORDER BY date DESC")
    suspend fun getAll(): List<RecentSearch>

    @Query("SELECT * FROM RecentSearch Where uid = :uid")
    suspend fun get(uid: Int): RecentSearch

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(search: RecentSearch)

    @Update
    suspend fun update(search: RecentSearch)

    @Delete
    suspend fun delete(search: RecentSearch)

    @Query("DELETE FROM RecentSearch")
    suspend fun deleteAll()

}