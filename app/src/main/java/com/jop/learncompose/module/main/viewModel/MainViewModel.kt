package com.jop.learncompose.module.main.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jop.learncompose.data.models.Note
import com.jop.learncompose.data.repositories.NoteRepository
import com.jop.learncompose.data.AppData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: NoteRepository, private val appData: AppData) : ViewModel(){
    private val _notes = MutableStateFlow(mapOf<Boolean, List<Note>>())
    val notes = _notes.asStateFlow()

    fun getAllNote(){
        viewModelScope.launch {
            repository.getAllNotes().collect{ notes ->
                val groupBy = notes.groupBy { it.isFavourite }
                _notes.value = groupBy
            }
        }
    }

    fun setIsGrid(isGrid: Boolean) = appData.setIsGrid(isGrid)

    fun getIsGrid() = appData.getIsGrid()
}