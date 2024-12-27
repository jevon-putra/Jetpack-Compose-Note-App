package com.jop.learncompose.module.manageNote.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.jop.learncompose.data.models.Note
import com.jop.learncompose.data.repositories.NoteRepository
import com.jop.learncompose.ui.theme.blueBackground
import com.jop.learncompose.ui.theme.brownBackground
import com.jop.learncompose.ui.theme.greenBackground
import com.jop.learncompose.ui.theme.orangeDarkBackground
import com.jop.learncompose.ui.theme.orangeLightBackground
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ManageNoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel(){
    private val _note: Note = Note()
    val note: MutableState<Note> = mutableStateOf(_note)
    val colors = listOf(blueBackground, greenBackground, orangeLightBackground, orangeDarkBackground, brownBackground)

    fun insertOrUpdateNote(title: String, message: String, isFavourite: Boolean, backgroundColor: String){
        note.value = Note(id = note.value.id, title = title, message = message, isFavourite = isFavourite, backgroundColor = backgroundColor, date = Date().time)
        viewModelScope.launch {
            if(note.value.id <= 0){
                repository.insertNote(note.value)
            } else {
                repository.updateNote(note.value)
            }
        }
    }

    fun archiveNote(){
        viewModelScope.launch {
            repository.archiveNote(note.value.id)
        }
    }

    fun unArchiveNote(){
        viewModelScope.launch {
            repository.unArchiveNote(note.value.id)
        }
    }

    fun handleOnBackPress(navController: NavHostController){
        navController.previousBackStackEntry?.savedStateHandle?.remove<Note>("note")
        navController.popBackStack()
    }
}