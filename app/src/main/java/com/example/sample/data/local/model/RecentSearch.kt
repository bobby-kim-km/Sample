package com.example.sample.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class RecentSearch(
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "date")
    val date: Long = Calendar.getInstance().timeInMillis
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}
