package com.jop.learncompose.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@Composable
fun GeneralAlertDialog(stateOpen: MutableState<Boolean>, title: String, message: String, btnConfirmText: String, btnCancelText: String,  btnConfirmOnClick: () -> Unit, btnCancelOnClick: () -> Unit = {}){
    if(stateOpen.value){
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.inverseSurface
                    )
                )
            },
            onDismissRequest = {
                stateOpen.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        stateOpen.value = false
                        btnConfirmOnClick()
                    }
                ) {
                    Text(
                        text = btnConfirmText,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        stateOpen.value = false
                        btnCancelOnClick()
                    }
                ) {
                    Text(
                        text = btnCancelText,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        )
    }
}