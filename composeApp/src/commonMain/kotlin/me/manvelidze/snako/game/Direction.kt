package me.manvelidze.snako.game

import androidx.compose.ui.input.key.Key

enum class Direction(val reverse: Int) {
    Top(1),
    Bottom(0),
    Left(3),
    Right(2);

    companion object {
        fun fromKey(key: Key): Direction? {
            return when (key) {
                Key.DirectionUp -> Top
                Key.DirectionLeft -> Left
                Key.DirectionRight -> Right
                Key.DirectionDown -> Bottom
                else -> null
            }
        }

        fun fromKey(key: String): Direction? {
            return when (key) {
                "ArrowLeft" -> Left
                "ArrowRight" -> Right
                "ArrowUp" -> Top
                "ArrowDown" -> Bottom
                else -> null
            }
        }
    }
}