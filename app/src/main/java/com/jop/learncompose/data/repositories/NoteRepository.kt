package com.jop.learncompose.data.repositories

import com.jop.learncompose.data.models.Note
import com.jop.learncompose.data.local.dao.NoteDao
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao) {

    fun getAllNotes() = noteDao.getNotes()

    fun getArchiveNotes() = noteDao.getArchiveNotes()

    suspend fun updateNote(note: Note) = noteDao.updateNote(note)

    suspend fun insertNote(note: Note) = noteDao.insertNote(note)

    suspend fun archiveNote(id: Int) = noteDao.archiveNote(id)

    suspend fun unArchiveNote(id: Int) = noteDao.unArchiveNote(id)

    suspend fun deleteAllArchiveNote() = noteDao.deleteAllArchiveNote()

    suspend fun deleteAll() = noteDao.deleteAll()
}