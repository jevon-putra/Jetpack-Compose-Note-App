package com.jop.learncompose.module.main.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.jop.learncompose.ui.component.CustomToolbarPrimary
import com.jop.learncompose.ui.component.ItemNote
import com.jop.learncompose.module.main.viewModel.MainViewModel
import com.jop.learncompose.ui.component.GeneralEmptyState
import com.jop.learncompose.ui.routes.Route
import com.jop.learncompose.ui.theme.Padding

@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel = hiltViewModel()){
    val isGrid = remember { mutableStateOf(true) }
    val notesGroup = viewModel.notes.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllNote()
        isGrid.value = viewModel.getIsGrid()
    }

    Scaffold(
        topBar = {
            CustomToolbarPrimary(
                actions = {
                    IconButton(onClick = { navController.navigate(Route.ARCHIVE_NOTE) }) {
                        Image(
                            painter = painterResource(id =  R.drawable.ic_archive),
                            contentDescription = "Archive Note",
                            colorFilter = ColorFilter.tint(
                                color = MaterialTheme.colorScheme.onSurface
                            )
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
        floatingActionButton = {
            LargeFloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(Padding.PADDING_24),
                onClick = { navController.navigate(Route.MANAGE_NOTE) },
            ) {
                Icon(Icons.Filled.Add,
                    contentDescription = "Add Note",
                    modifier = Modifier.size(Padding.PADDING_24 * 2)
                )
            }
        }
    ) { contentPadding ->
        Surface(modifier = Modifier.padding(contentPadding)){
            if(notesGroup.value.isEmpty()){
                GeneralEmptyState(
                    title = "No notes in here",
                    message = "Add your important notes\nby pressing the '+' button"
                )
            } else {
                LazyVerticalGrid(
                    modifier = Modifier.wrapContentHeight(),
                    columns = GridCells.Fixed(if(isGrid.value) 2 else 1),
                    contentPadding = PaddingValues(Padding.PADDING_16),
                    horizontalArrangement = Arrangement.spacedBy(Padding.PADDING_8),
                    verticalArrangement = Arrangement.spacedBy(Padding.PADDING_8),
                ) {
                    notesGroup.value.forEach { (b, notes) ->
                        item(span = {GridItemSpan(if(isGrid.value) 2 else 1)}){
                            ItemGroup(title = if(b) "Favorites" else "Others")
                        }
                        items(notes){ note ->
                            ItemNote(navController = navController, note = note, isGrid = isGrid.value)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemGroup(title: String){
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = title,
        style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onSurface
        )
    )
}