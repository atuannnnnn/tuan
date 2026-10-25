package com.example.smartcart.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Màu lấy từ thiết kế SmartCart. Đổi mã hex ở đây là cả app đổi theo.
val Green = Color(0xFF1B7A4B)
val GreenSoft = Color(0xFFE8F4EC)
val Ink = Color(0xFF14302A)
val Muted = Color(0xFF66756F)
val AppBackground = Color(0xFFF7F9F8)
val Line = Color(0xFFD9E0DD)
val Danger = Color(0xFFB3392F)
val DangerSoft = Color(0xFFFDECE9)

private val SmartCartColors = lightColorScheme(
    primary = Green,
    onPrimary = Color.White,
    primaryContainer = GreenSoft,
    onPrimaryContainer = Green,
    background = AppBackground,
    onBackground = Ink,
    surface = Color.White,
    onSurface = Ink,
    error = Danger,
    outline = Line,
)

// Thiết kế dùng font Inter. Muốn đúng 100%: thêm Inter vào res/font rồi gán FontFamily ở đây.
private val SmartCartTypography = Typography(
    headlineMedium = TextStyle(
        fontSize = 28.sp,
        lineHeight = 34.sp,
        fontWeight = FontWeight.Bold,
    ),
    titleMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
    ),
    bodyMedium = TextStyle(fontSize = 14.sp, lineHeight = 20.sp),
    bodySmall = TextStyle(fontSize = 12.sp, lineHeight = 16.sp),
)

@Composable
fun SmartCartTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = SmartCartColors,
        typography = SmartCartTypography,
        content = content,
    )
}
