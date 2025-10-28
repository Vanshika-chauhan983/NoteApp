package com.vanshika.notesapp.feature_note.presentation.add_edit_note.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun HintTextField(
    text: String,
    modifier: Modifier = Modifier,
    hint: String,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit,
    trailingIcon: @Composable (()-> Unit) = {}
){
    Box (
        modifier = modifier
    ){
        Row (
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
        ){
            Box(modifier = Modifier.weight(1f)){
                if (isHintVisible && text.isEmpty()){
                    Text(
                        text = hint,
                        style = textStyle,
                        color = Color.DarkGray
                    )
                }
                BasicTextField(
                    value = text,
                    onValueChange = onValueChange,
                    textStyle = textStyle,
                    singleLine = singleLine,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged {
                            onFocusChange(it)
                        }
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            trailingIcon()
        }
    }
}