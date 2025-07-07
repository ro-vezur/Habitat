package com.example.habitat.presentation.commonComponents.buttons

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import com.example.habitat.ui.theme.materialThemeExtensions.responsiveLayout
import kotlin.math.roundToInt

@Composable
fun CustomSwitcher(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    buttonWidth: Dp,
    buttonHeight: Dp,
    switchPadding: PaddingValues,
    onCheckValueChange: (isChecked: Boolean) -> Unit,
) {
    val switchSize by remember {
        mutableStateOf(buttonHeight - switchPadding.calculateTopPadding() * 2)
    }

    val interactionSource = remember { MutableInteractionSource() }

    val switcherCheckedXOffset = LocalDensity.current.run {
       (buttonWidth - (switchSize + switchPadding.calculateRightPadding(LayoutDirection.Ltr) * 2)).toPx().roundToInt()
    }

    val animateSwitcherOffset by animateIntOffsetAsState(
        targetValue = if(isChecked) {
            IntOffset(x = switcherCheckedXOffset, y = 0)
        } else IntOffset.Zero
    )

    val animateSwitcherColor by animateColorAsState(
        targetValue = if(isChecked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
    )

    Row(
        modifier = modifier
            .width(buttonWidth)
            .height(buttonHeight)
            .clip(CircleShape)
            .background(Color.Transparent)
            .border(MaterialTheme.responsiveLayout.border1, MaterialTheme.colorScheme.primary, CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onCheckValueChange(!isChecked)
            }
            .padding(switchPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
       Box(
           modifier = Modifier
               .offset { animateSwitcherOffset }
               .size(switchSize)
               .clip(CircleShape)
               .drawBehind { drawRect(color = animateSwitcherColor) }
       )
    }
}