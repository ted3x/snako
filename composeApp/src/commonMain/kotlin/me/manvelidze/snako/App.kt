package me.manvelidze.snako

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.layout.Layout
import me.manvelidze.snako.game.Direction
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(isScreenActive: Boolean, direction: Direction? = null) {
    val screenSize = remember { mutableStateOf<Pair<Int, Int>?>(null) }
    Layout(
        content = {
            if (screenSize.value != null) {
                val width = screenSize.value!!.first
                val height = screenSize.value!!.second
                val nearestWidth = if (width % 2 == 0) width else width - 1
                val nearestHeight = if (height % 2 == 0) height else height - 1
                Game(
                    screenSize = nearestWidth to nearestHeight,
                    isScreenActive = isScreenActive,
                    direction = direction
                )
            }
        },
        measurePolicy = { measurables, constraints ->
            // Use the max width and height from the constraints
            val width = constraints.maxWidth
            val height = constraints.maxHeight

            screenSize.value = Pair(width, height)
            println("Width: $width, height: $height")

            // Measure and place children composables
            val placeables = measurables.map { measurable ->
                measurable.measure(constraints)
            }

            layout(width, height) {
                var yPosition = 0
                placeables.forEach { placeable ->
                    placeable.placeRelative(x = 0, y = yPosition)
                    yPosition += placeable.height
                }
            }
        }
    )
}