package com.example.sample.di


import android.content.Context
import com.example.sample.data.local.AppDatabase
import com.example.sample.data.local.source.RecentSearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LocalDatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return AppDatabase.getInstance(applicationContext)
    }

    @Provides
    fun provideRecentSearchDao(appDatabase: AppDatabase): RecentSearchDao {
        return appDatabase.recentSearchDao()
    }



}
