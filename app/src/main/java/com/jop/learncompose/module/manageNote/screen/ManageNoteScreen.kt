package com.jop.learncompose.module.manageNote.screen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jop.learncompose.R
import com.jop.learncompose.data.models.Note
import com.jop.learncompose.module.manageNote.viewModel.ManageNoteViewModel
import com.jop.learncompose.ui.component.CustomTextField
import com.jop.learncompose.ui.component.CustomToolbar
import com.jop.learncompose.ui.theme.Padding
import com.jop.learncompose.ui.theme.surfaceDimLight
import com.jop.learncompose.utils.toColor
import com.jop.learncompose.utils.toHexCode
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ManageNoteScreen(navController: NavHostController, note: Note?, viewModel: ManageNoteViewModel = hiltViewModel()) {
    val dateFormat = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale("id", "ID"))
    val etTitle = remember { mutableStateOf(viewModel.note.value.title) }
    val etMessage = remember { mutableStateOf(viewModel.note.value.message) }
    val selectedColor = remember { mutableStateOf(Color.Transparent) }
    val animatedColor by animateColorAsState(selectedColor.value, label = "Background Color")
    val focusRequesterEtMessage = remember { FocusRequester() }
    val isFavourite = remember { mutableStateOf(viewModel.note.value.isFavourite) }
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if(note != null){
            viewModel.note.value = note
            etTitle.value = note.title
            etMessage.value = note.message
            selectedColor.value = note.backgroundColor.toColor()
            isFavourite.value = note.isFavourite
        } else {
            selectedColor.value = viewModel.colors.random()
        }
    }

    BackHandler {
        if((etTitle.value.isNotEmpty() || etMessage.value.isNotEmpty()) &&  (note == null || !note.isArchive)) {
            viewModel.insertOrUpdateNote(
                title = etTitle.value,
                message = etMessage.value,
                isFavourite = isFavourite.value,
                backgroundColor = selectedColor.value.toHexCode()
            )
        }
        viewModel.handleOnBackPress(navController)
    }

    Scaffold(
        modifier = Modifier.drawBehind { drawRect(animatedColor) },
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            CustomToolbar(
                canNavigateBack = true,
                color = TopAppBarDefaults.topAppBarColors(
                    containerColor = selectedColor.value,
                    actionIconContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                navigateUp = { onBackPressedDispatcher?.onBackPressed() },
                actions = {
                    if(!viewModel.note.value.isArchive){
                        IconButton(onClick = { isFavourite.value = !isFavourite.value }) {
                            Icon(
                                imageVector = if(isFavourite.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = "Favourite"
                            )
                        }
                    }
                    if(viewModel.note.value.id > 0 && !viewModel.note.value.isArchive){
                        IconButton(onClick = {
                            viewModel.archiveNote()
                            navController.popBackStack()
                            Toast.makeText(context, "Success archive note", Toast.LENGTH_LONG).show()
                        }) {
                            Image(
                                painter = painterResource(id =  R.drawable.ic_archive),
                                contentDescription = "Archive Note",
                                colorFilter = ColorFilter.tint(
                                    color = Color.White
                                )
                            )
                        }
                    }
                    if(viewModel.note.value.id > 0 && viewModel.note.value.isArchive){
                        IconButton(onClick = {
                            viewModel.unArchiveNote()
                            navController.popBackStack()
                            Toast.makeText(context, "Success unarchive note", Toast.LENGTH_LONG).show()
                        }) {
                            Image(
                                painter = painterResource(id =  R.drawable.ic_un_archive),
                                contentDescription = "Unarchive Note",
                                colorFilter = ColorFilter.tint(
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            if(note == null || !note.isArchive){
                ElevatedCard(
                    shape = RoundedCornerShape(0.dp),
                    colors = CardDefaults.cardColors(containerColor = selectedColor.value),
                    elevation = CardDefaults.cardElevation(defaultElevation = Padding.PADDING_8)
                ){
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(5) ,
                        modifier = Modifier
                            .navigationBarsPadding()
                            .padding(Padding.PADDING_16)
                            .height(Padding.PADDING_24 * 3),
                        horizontalArrangement = Arrangement.spacedBy(Padding.PADDING_16)
                    ) {
                        items(viewModel.colors){ item ->
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .height(Padding.PADDING_24 * 3)
                                .clip(RoundedCornerShape(Padding.PADDING_16))
                                .background(color = item)
                                .border(
                                    width = 1.dp,
                                    color = if (item.toString() == selectedColor.value.toString()) Color.White else item,
                                    shape = RoundedCornerShape(Padding.PADDING_16)
                                )
                                .clickable {
                                    selectedColor.value = item
                                },
                                contentAlignment = Alignment.Center
                            ){
                                if(item.toString() == selectedColor.value.toString()){
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = "Background Selected",
                                        modifier = Modifier.size(Padding.PADDING_16 * 2),
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .drawBehind { drawRect(animatedColor) }
                .padding(contentPadding)
                .padding(Padding.PADDING_16),
            verticalArrangement = Arrangement.spacedBy(Padding.PADDING_8)
        ) {
            CustomTextField(
                state = etTitle,
                placeHolder = "Title Note",
                textStyle = MaterialTheme.typography.headlineLarge.copy(
                    color = Color.White
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                enabled = note == null || !note.isArchive,
                singleLine = false,
                keyboardActions = KeyboardActions(
                    onNext = { focusRequesterEtMessage.requestFocus() }
                )
            )
            CustomTextField(
                modifier = Modifier.focusRequester(focusRequesterEtMessage),
                state = etMessage,
                placeHolder = "Note",
                textStyle = MaterialTheme.typography.bodySmall.copy(
                    color = Color.White
                ),
                enabled = note == null || !note.isArchive,
                singleLine = false,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
            )
            if(viewModel.note.value.id > 0){
                Text(
                    text = "Update at ${dateFormat.format(Date(viewModel.note.value.date)) }",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = surfaceDimLight,
                        fontSize = 10.sp,
                        lineHeight = 12.sp
                    )
                )
            }
        }
    }
}