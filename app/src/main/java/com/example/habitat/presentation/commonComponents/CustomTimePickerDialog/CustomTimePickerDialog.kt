package com.example.habitat.presentation.commonComponents.CustomTimePickerDialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.habitat.ui.theme.materialThemeExtensions.primaryButtonColor
import com.example.habitat.ui.theme.materialThemeExtensions.responsiveLayout
import com.example.habitat.ui.theme.materialThemeExtensions.textColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePickerDialog(
    selectedHours: Int,
    selectedMinutes: Int,
    onDismiss: () -> Unit,
    onTimeSelect: (hour: Int, minutes: Int) -> Unit,
) {

    var hour by remember {
        mutableStateOf(selectedHours)
    }

    var minute by remember {
        mutableStateOf(selectedMinutes)
    }

    BasicAlertDialog(
        modifier = Modifier,
        onDismissRequest = onDismiss,
        content = {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius1))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(
                        vertical = MaterialTheme.responsiveLayout.paddingSmall
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularClock(
                        values = 24,
                        initialValue = selectedHours,
                        onValueChange = { newHours ->
                            hour = newHours
                        }
                    )

                    Spacer(Modifier.width(MaterialTheme.responsiveLayout.paddingMedium))

                    CircularClock(
                        values = 60,
                        initialValue = selectedMinutes,
                        onValueChange = { newMinutes ->
                            minute = newMinutes
                        }
                    )
                }

                Button(
                    modifier = Modifier
                        .padding(top = MaterialTheme.responsiveLayout.paddingSmall)
                        .width(MaterialTheme.responsiveLayout.doneTimePickerButtonWidth)
                        .height(MaterialTheme.responsiveLayout.doneTimePickerButtonHeight),
                    shape = RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius1),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryButtonColor,
                        contentColor = MaterialTheme.colorScheme.textColor
                    ),
                    onClick = { onTimeSelect(hour, minute) }
                ) {
                    Text(
                        text = "Done",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.textColor
                    )
                }
            }
        }
    )
}