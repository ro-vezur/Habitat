package com.example.habitat.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.vector.ImageVector

enum class HabitsCategories(val title: String, val icon: ImageVector) {
    HEALTH("Health", Icons.Default.Favorite),
    FITNESS("Fitness", Icons.Default.FitnessCenter),
    STUDY("Study", Icons.Default.Book),
    HOBBY("Hobby", Icons.Default.Brush),
    SOCIAL("Social", Icons.Default.People),
    MINDFULNESS("Mindfulness", Icons.Default.SelfImprovement),
    PRODUCTIVITY("Productivity", Icons.Default.Work),
    PERSONAL("Personal", Icons.Default.Person),
    FINANCE("Finance", Icons.Default.AttachMoney),
}