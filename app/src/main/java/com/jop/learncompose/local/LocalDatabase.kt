package com.jop.learncompose.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jop.learncompose.data.Note
import com.jop.learncompose.local.dao.NoteDao

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}