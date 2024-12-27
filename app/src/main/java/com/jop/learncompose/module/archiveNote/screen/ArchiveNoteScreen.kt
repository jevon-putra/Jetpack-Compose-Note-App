package com.jop.learncompose.module.archiveNote.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jop.learncompose.R
import com.jop.learncompose.module.archiveNote.viewModel.ArchiveNoteViewModel
import com.jop.learncompose.ui.component.CustomToolbar
import com.jop.learncompose.ui.component.GeneralAlertDialog
import com.jop.learncompose.ui.component.GeneralEmptyState
import com.jop.learncompose.ui.component.ItemNote
import com.jop.learncompose.ui.theme.Padding

@Composable
fun ArchiveNoteScreen(navController: NavHostController, viewModel: ArchiveNoteViewModel = hiltViewModel()) {
    val isGrid = remember { mutableStateOf(true) }
    val notes = viewModel.notes.collectAsState()
    val showAlertDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getArchiveNotes()
        isGrid.value = viewModel.getIsGrid()
    }

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            CustomToolbar(
                canNavigateBack = true,
                title = "Archive Note",
                color = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                ),
                navigateUp = { navController.popBackStack() },
                actions = {
                    IconButton(onClick = {
                        showAlertDialog.value = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete"
                        )
                    }
                    IconButton(onClick = {
                        isGrid.value = !isGrid.value
                        viewModel.setIsGrid(isGrid.value)
                    }) {
                        Image(
                            painter = painterResource(id = if(isGrid.value) R.drawable.ic_list else R.drawable.ic_grid),
                            contentDescription = if(isGrid.value) "List" else "Grid",
                            colorFilter = ColorFilter.tint(
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { contentPadding ->
        if(showAlertDialog.value){
            GeneralAlertDialog(
                stateOpen = showAlertDialog,
                title = "Delete all archive records ?",
                message = "All data will be permanently deleted",
                btnConfirmText = "Confrim".uppercase(),
                btnCancelText = "Cancel".uppercase(),
                btnConfirmOnClick = { viewModel.deleteAllArchive() },
            )
        }

        Surface(modifier = Modifier.padding(contentPadding)){
            if(notes.value.isEmpty()){
                GeneralEmptyState(
                    title = "No notes in Archive",
                    message = "Contains notes that have been archived"
                )
            } else {
                LazyVerticalGrid(
                    modifier = Modifier.wrapContentHeight(),
                    columns = GridCells.Fixed(if(isGrid.value) 2 else 1),
                    contentPadding = PaddingValues(Padding.PADDING_16),
                    horizontalArrangement = Arrangement.spacedBy(Padding.PADDING_8),
                    verticalArrangement = Arrangement.spacedBy(Padding.PADDING_8),
                ) {
                    items(notes.value){ note ->
                        ItemNote(navController = navController, note = note, isGrid = isGrid.value)
                    }
                }
            }
        }
    }
}