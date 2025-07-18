package com.example.habitat.presentation.commonComponents.InteractiveFields

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.habitat.ui.theme.HabitatTheme
import com.example.habitat.ui.theme.materialThemeExtensions.responsiveLayout
import androidx.compose.runtime.getValue
import com.example.habitat.ui.theme.materialThemeExtensions.textColorSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeHolderText: String = "",
    colors: TextFieldColors = baseCustomTextFieldColors(),
    keyboardOptions: KeyboardOptions = KeyboardOptions()
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        textStyle = MaterialTheme.typography.headlineSmall.copy(
            color = if(isFocused) colors.focusedTextColor else colors.unfocusedTextColor
        ),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        interactionSource = interactionSource
    ) { innerTextField ->
        TextFieldDefaults.DecorationBox(
            value = value,
            innerTextField = innerTextField,
            enabled = true,
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            contentPadding = PaddingValues(
                start = MaterialTheme.responsiveLayout.spacingMedium
            ),
            placeholder = {
                Text(
                    text = placeHolderText,
                    style = MaterialTheme.typography.headlineSmall,
                    color = if(isFocused) colors.focusedTextColor else colors.unfocusedTextColor
                )
            },
            colors = colors
        )
    }
}

@Composable
fun baseCustomTextFieldColors(): TextFieldColors = TextFieldDefaults.colors(
    unfocusedTextColor = MaterialTheme.colorScheme.secondary,
    focusedTextColor = MaterialTheme.colorScheme.primary,
    unfocusedContainerColor = Color.Transparent,
    focusedContainerColor = Color.Transparent,
    unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
    focusedIndicatorColor = MaterialTheme.colorScheme.secondary
)

@Preview(showBackground = true)
@Composable
private fun CustomTextInputFieldPreview() {
    HabitatTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextInputField(
                modifier = Modifier
                    .height(MaterialTheme.responsiveLayout.inputTextFieldHeight),
                value = "",
                onValueChange = {

                },
                placeHolderText = "Firstly, what's your name?"
            )
        }
    }
}