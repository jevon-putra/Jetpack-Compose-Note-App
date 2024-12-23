package com.jop.learncompose.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity("notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "message")
    val message: String,
    @ColumnInfo(name = "isFavourite")
    val isFavourite: Boolean = false,
    @ColumnInfo(name = "backgroundColor")
    val backgroundColor: String,
    @ColumnInfo(name = "date")
    val updateAt: Date = Date()
)