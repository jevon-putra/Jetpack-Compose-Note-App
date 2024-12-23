package com.jop.learncompose.data.repositories

import com.jop.learncompose.data.Note
import com.jop.learncompose.local.dao.NoteDao
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao) {

    fun getAllNotes() = noteDao.getNotes()

    suspend fun updateNote(note: Note) = noteDao.updateNote(note)

    suspend fun insertNote(note: Note) = noteDao.insertNote(note)

    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)

    suspend fun deleteAll() = noteDao.deleteAll()
}