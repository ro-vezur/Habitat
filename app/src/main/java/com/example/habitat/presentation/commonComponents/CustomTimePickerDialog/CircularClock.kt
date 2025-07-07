package com.example.habitat.presentation.commonComponents.CustomTimePickerDialog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.example.habitat.ui.theme.materialThemeExtensions.responsiveLayout

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CircularClock(
    values : Int,
    initialValue: Int,
    height: Dp = MaterialTheme.responsiveLayout.timePickerCarouselHeight,
    textStyle: TextStyle = MaterialTheme.typography.displaySmall,
    textScaleFactor: Float = 1.4f,
    numberOfDisplayedItems: Int = 3,
    onValueChange: (newValue: Int) -> Unit,
) {
    val cellHeight = height / numberOfDisplayedItems
    val cellHalfHeight = LocalDensity.current.run { cellHeight.toPx() / 2f }

    val expandedSize = values * 10_000_000
    val initialListPoint = expandedSize / 2
    val targetIndex = initialListPoint + initialValue - 1

    val scrollState = rememberLazyListState(targetIndex)
    var selectedValueIndex by remember { mutableIntStateOf(0) }

    LazyColumn(
        modifier = Modifier
            .height(height),
        state = scrollState,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = scrollState)
    ) {
        items(
            count = expandedSize,
            itemContent = {
                val num = (it % values)

                Box(
                    modifier = Modifier
                        .size(cellHeight)
                        .onGloballyPositioned { coordinates ->
                            val y = coordinates.positionInParent().y - cellHalfHeight
                            val parentHalfHeight = (cellHalfHeight * numberOfDisplayedItems)
                            val isSelected =
                                (y > parentHalfHeight - cellHalfHeight && y < parentHalfHeight + cellHalfHeight)

                            val index = it - 1

                            if (isSelected) {
                                selectedValueIndex = index
                                onValueChange(index % values)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier,
                        text = "%02d".format(num),
                        style = textStyle.copy(
                            color = if(selectedValueIndex == it) {
                                MaterialTheme.colorScheme.primary
                            } else MaterialTheme.colorScheme.secondary,
                            fontSize = if(selectedValueIndex == it) {
                                textStyle.fontSize * textScaleFactor
                            } else textStyle.fontSize
                        )
                    )
                }
            }
        )
    }
}