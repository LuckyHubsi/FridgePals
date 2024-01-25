package com.example.fridgepals.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.fridgepals.R

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.cherry_bomb)),
        fontWeight = FontWeight.Normal,
        fontSize = 38.sp,
        lineHeight = 48.sp,
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
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.dmsans_regular)),
        fontWeight = FontWeight.ExtraBold,
        fontSize = 25.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp,
        color = colors.DarkBrown
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.dmsans_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.sp,
        color = colors.Brown
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.dmsans_regular)),
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 12.sp,
        letterSpacing = 0.sp,
        color = colors.LightBrown
    ),
)