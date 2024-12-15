package me.manvelidze.snako

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlinx.browser.window
import me.manvelidze.snako.game.Direction
import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport(document.body!!) {
        val direction = remember { mutableStateOf(Direction.entries.random()) }
        LaunchedEffect(Unit) {
            val handleKeyDown: (Event) -> Unit = { event ->
                println(event.toString())
                (event as KeyboardEvent).let {
                    Direction.fromKey(it.key)?.let { direction.value = it }
                }
            }
            window.addEventListener("keydown", handleKeyDown)
        }
        App(document.hasFocus(), direction.value)
    }
}