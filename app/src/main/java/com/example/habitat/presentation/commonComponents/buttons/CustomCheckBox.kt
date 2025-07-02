package com.example.habitat.presentation.commonComponents.buttons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.Dp
import com.example.habitat.ui.theme.materialThemeExtensions.responsiveLayout
import com.example.habitat.ui.theme.materialThemeExtensions.textColor

@Composable
fun CustomCheckBox(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onCheck: () -> Unit,
    containerColor: Color = MaterialTheme.colorScheme.secondary,
    checkColor: Color = MaterialTheme.colorScheme.textColor,
    shape: RoundedCornerShape = RoundedCornerShape(MaterialTheme.responsiveLayout.roundedCornerRadius1),
    containerSize : Dp = MaterialTheme.responsiveLayout.checkBoxSize,
    iconSize: Dp = MaterialTheme.responsiveLayout.iconMedium,
) {
    val toggleableState = ToggleableState(isChecked)

    Box(
        modifier = modifier
            .size(containerSize)
            .clip(shape)
            .background(containerColor)
            .triStateToggleable(
                state = toggleableState,
                onClick = onCheck,
                role = Role.Checkbox,
            ),
        contentAlignment = Alignment.Center
    ) {

        AnimatedVisibility(
            visible = isChecked,
            enter = fadeIn() + scaleIn() + expandVertically(expandFrom = Alignment.Top),
            exit = fadeOut() + scaleOut() + shrinkVertically(shrinkTowards = Alignment.Top),
        ) {
            Icon(
                modifier = Modifier
                    .size(iconSize),
                imageVector = Icons.Default.Check,
                contentDescription = "check",
                tint = checkColor
            )
        }
    }
}