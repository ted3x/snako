package me.manvelidze.snako.game

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Stable
class Position(startX: Int, startY: Int) {
    var x by mutableStateOf<Int>(startX)
    var y by mutableStateOf<Int>(startY)


    fun intersects(position: Position): Boolean {
        return x < position.x + 16f &&
                x + 16f > position.x &&
                y < position.y + 16f &&
                y + 16f > position.y
    }

    companion object {
        fun random(width: Int, height: Int) = Position(
            (0..width).random(),
            (0..height).random()
        )
    }
}
