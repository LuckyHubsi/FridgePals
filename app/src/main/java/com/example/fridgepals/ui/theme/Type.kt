package com.example.fridgepals.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.fridgepals.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.cherry_bomb)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.cherry_bomb)),
        fontWeight = FontWeight.Normal,
        fontSize = 42.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
        color = colors.NotQuiteWhite
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.cherry_bomb)),
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        color = colors.NotQuiteWhite
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.cherry_bomb)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.sp,
        color = colors.NotQuiteWhite
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.cherry_bomb)),
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)