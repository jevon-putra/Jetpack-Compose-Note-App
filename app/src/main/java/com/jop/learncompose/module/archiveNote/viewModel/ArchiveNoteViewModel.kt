package com.jop.learncompose.module.archiveNote.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jop.learncompose.data.models.Note
import com.jop.learncompose.data.repositories.NoteRepository
import com.jop.learncompose.data.AppData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ArchiveNoteViewModel @Inject constructor(private val repository: NoteRepository, private val appData: AppData) : ViewModel(){
    private val _notes = MutableStateFlow(listOf<Note>())
    val notes = _notes.asStateFlow()

    fun getArchiveNotes(){
        viewModelScope.launch {
            repository.getArchiveNotes().collect{ notes ->
                _notes.value = notes
            }
        }
    }

    fun deleteAllArchive(){
        runBlocking {
            repository.deleteAllArchiveNote()
            getArchiveNotes()
        }
    }

    fun setIsGrid(isGrid: Boolean) = appData.setIsGrid(isGrid)

    fun getIsGrid() = appData.getIsGrid()
}