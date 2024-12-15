package me.manvelidze.snako

import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.changedToDown
import androidx.compose.ui.input.pointer.changedToDownIgnoreConsumed
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.changedToUpIgnoreConsumed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.util.fastAll
import me.manvelidze.snako.game.Direction
import kotlin.math.abs

@OptIn(ExperimentalMaterialApi::class)
@Composable
actual fun Modifier.handleDirection(onChange: (Direction) -> Unit): Modifier {
    return this then pointerInput(Unit) {
        detectSwipes { direction ->
            onChange.invoke(direction)
        }
    }
}

suspend fun PointerInputScope.detectSwipes(onSwipeDetected: (Direction) -> Unit) {
    awaitPointerEventScope {
        while (true) {
            val down = awaitFirstDown()
            down.consume() // Consume the event to prevent propagation
            val start = down.position
            val up = awaitFirstUp()
            val end = up.position
            val diffX = end.x - start.x
            val diffY = end.y - start.y

            when {
                abs(diffX) > abs(diffY) -> {
                    println(abs(diffX))
                    if (abs(diffX) < 20) continue
                    if (diffX > 0) {
                        onSwipeDetected(Direction.Right)
                    } else {
                        onSwipeDetected(Direction.Left)
                    }
                }

                abs(diffY) > abs(diffX) -> {
                    println(abs(diffX))
                    if (abs(diffY) < 20) continue
                    if (diffY > 0) {
                        onSwipeDetected(Direction.Bottom)
                    } else {
                        onSwipeDetected(Direction.Top)
                    }
                }
            }
        }
    }
}

suspend fun AwaitPointerEventScope.awaitFirstUp(
    requireUnconsumed: Boolean = true,
    pass: PointerEventPass = PointerEventPass.Main,
): PointerInputChange {
    var event: PointerEvent
    do {
        event = awaitPointerEvent(pass)
    } while (
        !event.changes.fastAll {
            if (requireUnconsumed) it.changedToUp() else it.changedToUpIgnoreConsumed()
        }
    )
    return event.changes[0]
}