package com.jop.learncompose.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.jop.learncompose.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomToolbar(title: String = "", canNavigateBack: Boolean = false, navigateUp: () -> Unit = {}, color: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),actions: @Composable RowScope.() -> Unit = {}){
    TopAppBar(
        title = { if(title.isNotEmpty()) Text(text = title, style = MaterialTheme.typography.headlineLarge) },
        actions = actions,
        colors = color,
        navigationIcon = {
            if(canNavigateBack){
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = color.navigationIconContentColor
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomToolbarPrimary(actions: @Composable RowScope.() -> Unit = {}){
    TopAppBar(
        title = {
            Row {
                Text(
                    text = "Note",
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = ".me",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            actionIconContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    )
}

@ExperimentalMaterial3Api
@Preview()
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewToolbar(){
    AppTheme {
        Surface {
            CustomToolbar(
                title = "Preview"
            )
        }
    }
}