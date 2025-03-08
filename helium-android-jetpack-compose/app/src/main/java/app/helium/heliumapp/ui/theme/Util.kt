package app.helium.heliumapp.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode


fun createGradientEffect(
    colors: List<Color>,
    isVertical: Boolean = true
): Brush {

    val endOffset = if (isVertical) {
        Offset(Float.POSITIVE_INFINITY, 0f)
    } else {
        Offset(0f, Float.POSITIVE_INFINITY)
    }

    return Brush.linearGradient(
        colors = colors,
        start = Offset(0f, 0f),
        end = endOffset,
        tileMode = TileMode.Clamp
    )
}
