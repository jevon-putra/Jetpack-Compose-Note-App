package com.jop.learncompose.ui.module.main.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.jop.learncompose.ui.module.main.viewModel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()){
    val items = viewModel.items.collectAsState().value

    Surface(modifier = Modifier.fillMaxSize()) {

    }
}