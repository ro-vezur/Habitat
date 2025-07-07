package com.example.habitat

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.IntOffset

const val REGISTER_PAGE_COUNT = 3
const val INITIAL_REGISTER_PAGE = 1

const val HABITS_LOCAL_DATA_BASE_NAME = "habits"

val datePickerVisibilityAnimationSpec: FiniteAnimationSpec<IntOffset> = tween(
    durationMillis = 350
)