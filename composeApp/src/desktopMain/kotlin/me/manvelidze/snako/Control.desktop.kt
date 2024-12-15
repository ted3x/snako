package me.manvelidze.snako

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.manvelidze.snako.game.Direction

@Composable
actual fun Modifier.handleDirection(onChange: (Direction) -> Unit): Modifier {
return this
}