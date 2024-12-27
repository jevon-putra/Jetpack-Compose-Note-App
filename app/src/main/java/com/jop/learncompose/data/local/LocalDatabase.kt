package com.jop.learncompose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jop.learncompose.data.models.Note
import com.jop.learncompose.data.local.dao.NoteDao

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}