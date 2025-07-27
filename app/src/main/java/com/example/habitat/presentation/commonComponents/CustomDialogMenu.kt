package com.example.habitat.presentation.commonComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.example.habitat.ui.theme.materialThemeExtensions.primaryButtonColor
import com.example.habitat.ui.theme.materialThemeExtensions.responsiveLayout
import com.example.habitat.ui.theme.materialThemeExtensions.textColor

@Composable
fun CustomDialogMenu(
    text: String,
    onDismiss: () -> Unit,
    onAccept: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius1))
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier
                    .padding(top = MaterialTheme.responsiveLayout.paddingMedium)
                    .fillMaxWidth(0.9f),
                text = text,
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )

            Row(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.responsiveLayout.paddingSmall, vertical = MaterialTheme.responsiveLayout.paddingMedium),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.responsiveLayout.spacingLarge)
            ) {
                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(MaterialTheme.responsiveLayout.buttonHeight1),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryButtonColor
                    ),
                    shape = RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius2),
                    onClick = {
                        onAccept()
                    }
                ) {
                    Text(
                        text = "Yes",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.textColor
                    )
                }

                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .height(MaterialTheme.responsiveLayout.buttonHeight1),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryButtonColor
                    ),
                    shape = RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius2),
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text(
                        text = "No",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.textColor
                    )
                }
            }
        }
    }
}