package com.example.fridgepals.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.fridgepals.ui.theme.colors.Brown
import com.example.fridgepals.ui.theme.colors.DarkBrown
import com.example.fridgepals.ui.theme.colors.GreenBlue
import com.example.fridgepals.ui.theme.colors.LightBlue
import com.example.fridgepals.ui.theme.colors.NotQuiteWhite
import com.example.fridgepals.ui.theme.colors.YellowOrange

// Not used
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = GreenBlue,
    secondary = YellowOrange,
    tertiary = LightBlue,

    onPrimary = NotQuiteWhite,

    onSecondary = DarkBrown,
    onTertiary = Brown,

    background = NotQuiteWhite,

    /*

    surface = Color(0xFF58A8A8),
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),


     */
)

@Composable
fun FridgePalsTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}