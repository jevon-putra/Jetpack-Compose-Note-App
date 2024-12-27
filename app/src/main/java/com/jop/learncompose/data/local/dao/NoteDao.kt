package com.jop.learncompose.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.jop.learncompose.data.models.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM `notes` WHERE `isArchive` = 0 ORDER BY isFavourite DESC, date ASC")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM `notes` WHERE `isArchive` = 1 ORDER BY isFavourite DESC, date ASC")
    fun getArchiveNotes(): Flow<List<Note>>

    @Update
    suspend fun updateNote(note: Note)

    @Insert
    suspend fun insertNote(note: Note)

    @Query("UPDATE `notes` SET `isArchive` = 1 WHERE id =:id")
    suspend fun archiveNote(id: Int)

    @Query("UPDATE `notes` SET `isArchive` = 0 WHERE id =:id")
    suspend fun unArchiveNote(id: Int)

    @Query("DELETE FROM `notes` WHERE `isArchive` = 1")
    suspend fun deleteAllArchiveNote()

    @Query("DELETE FROM `notes`")
    suspend fun deleteAll()
}