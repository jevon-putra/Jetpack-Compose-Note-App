package com.jop.learncompose.ui.component

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle

@Composable
fun CustomTextField(modifier: Modifier = Modifier, state: MutableState<String>,
                    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
                    singleLine: Boolean = true, enabled: Boolean = true, placeHolder: String,
                    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
                    keyboardActions: KeyboardActions = KeyboardActions.Default,
){
    BasicTextField(
        value = state.value,
        modifier = modifier,
        onValueChange = { state.value = it },
        textStyle = textStyle.copy(
            platformStyle = PlatformTextStyle(
                includeFontPadding = false
            )
        ),
        singleLine = singleLine,
        enabled = enabled,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        decorationBox = { innerTextField ->
            if (state.value.isEmpty()) {
                Text(
                    text = placeHolder,
                    style = textStyle
                )
            }
            innerTextField()
        }
    )
}