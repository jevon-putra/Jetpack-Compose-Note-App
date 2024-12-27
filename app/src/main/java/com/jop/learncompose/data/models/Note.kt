package com.jop.learncompose.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity("notes")
@Parcelize
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "message")
    val message: String = "",
    @ColumnInfo(name = "isFavourite")
    val isFavourite: Boolean = false,
    @ColumnInfo(name = "backgroundColor")
    val backgroundColor: String = "FFFFFF",
    @ColumnInfo(name = "date")
    val date: Long = 0L,
    @ColumnInfo(name = "isArchive")
    val isArchive: Boolean = false,
): Parcelable