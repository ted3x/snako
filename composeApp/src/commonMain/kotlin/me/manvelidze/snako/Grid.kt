package me.manvelidze.snako

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.unit.dp

@Composable
fun Grid(w: Int, h: Int, modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier
            .border(1.dp, Black)
            .width(w.dp)
            .height(h.dp)
    ) {
        content.invoke()
    }
}