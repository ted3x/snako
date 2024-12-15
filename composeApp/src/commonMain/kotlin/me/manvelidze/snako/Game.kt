package me.manvelidze.snako

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import me.manvelidze.snako.game.Direction

@Composable
fun Game(
    screenSize: Pair<Int, Int>,
    direction: Direction? = null,
    isScreenActive: Boolean,
    modifier: Modifier = Modifier
) {
    val screenWidth = screenSize.first
    val screenHeight = screenSize.second
    val gameManager = remember(screenSize) { GameManager(screenWidth, screenHeight) }
    LaunchedEffect(direction) { direction?.let { gameManager.onDirectionChange(it) } }
    LaunchedEffect(isScreenActive) { gameManager.isScreenActive = isScreenActive }
    Box(modifier.handleDirection { gameManager.onDirectionChange(it) }) {
        Grid(screenWidth, screenHeight) {
            Canvas(modifier = Modifier.drawBehind {
                drawRect(
                    color = Color.Red,
                    topLeft = Offset(
                        gameManager.applePosition.x.toFloat(),
                        gameManager.applePosition.y.toFloat()
                    ),
                    size = Size(20f, 20f)
                )
            }) {
                gameManager.particles.forEach {
                    drawRect(
                        color = Color.Black,
                        Offset(it.x.toFloat(), it.y.toFloat()),
                        size = Size(20f, 20f)
                    )
                }
            }
        }
    }
}