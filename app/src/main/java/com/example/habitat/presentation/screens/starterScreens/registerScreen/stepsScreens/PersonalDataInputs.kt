package com.example.habitat.presentation.screens.starterScreens.registerScreen.stepsScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.example.habitat.presentation.commonComponents.InteractiveFields.CustomTextInputField
import com.example.habitat.presentation.screens.starterScreens.registerScreen.RegisterEvent
import com.example.habitat.ui.theme.materialThemeExtensions.responsiveLayout

@Composable
fun Step1screen(
    nameInput: String,
    ageInput: String,
    livePlaceInput: String,
    executeEvent: (RegisterEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        CustomTextInputField(
            modifier = Modifier
                .padding(top = MaterialTheme.responsiveLayout.paddingExtraLarge)
                .fillMaxWidth()
                .height(MaterialTheme.responsiveLayout.inputTextFieldHeight),
            value = nameInput,
            onValueChange = { newValue -> executeEvent(RegisterEvent.UpdateNameInput(newValue)) },
            placeHolderText = "Firstly, what's your name?",
        )

        CustomTextInputField(
            modifier = Modifier
                .padding(top = MaterialTheme.responsiveLayout.paddingExtraLarge)
                .fillMaxWidth()
                .height(MaterialTheme.responsiveLayout.inputTextFieldHeight),
            value = ageInput,
            onValueChange = { newValue ->
                val newAgeNumbers = newValue.ifBlank { "0" }.toInt()
                if(newAgeNumbers <= 120) {
                    executeEvent(RegisterEvent.UpdateAgeInput(newAgeNumbers))
                } else {
                    executeEvent(RegisterEvent.UpdateAgeInput(120))
                } },
            placeHolderText = "How old are you?",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

        CustomTextInputField(
            modifier = Modifier
                .padding(top = MaterialTheme.responsiveLayout.paddingExtraLarge)
                .fillMaxWidth()
                .height(MaterialTheme.responsiveLayout.inputTextFieldHeight),
            value = livePlaceInput,
            onValueChange = { newValue -> executeEvent(RegisterEvent.UpdatePlaceInput(newValue))},
            placeHolderText = "Where do you live",
        )
    }
}