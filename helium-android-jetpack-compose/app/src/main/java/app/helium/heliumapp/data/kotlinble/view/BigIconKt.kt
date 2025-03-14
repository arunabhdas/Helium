package app.helium.heliumapp.data.kotlinble.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun BigIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    size: Dp = 80.dp,
    color: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
) {
    Image(
        imageVector = imageVector,
        contentDescription = null,
        modifier = modifier.size(size),
        colorFilter = ColorFilter.tint(color),
    )
}

@Composable
internal fun BigIcon(
    painterResource: Painter,
    modifier: Modifier = Modifier,
    size: Dp = 80.dp,
    color: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
) {
    Image(
        painter = painterResource,
        contentDescription = null,
        modifier = modifier.size(size),
        colorFilter = ColorFilter.tint(color),
    )
}