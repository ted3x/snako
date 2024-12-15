package me.manvelidze.snako

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.key.key
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import me.manvelidze.snako.game.Direction
import java.awt.event.WindowEvent
import java.awt.event.WindowListener

fun main() = application {
    val direction = remember { mutableStateOf(Direction.entries.random()) }
    var isFocused by remember { mutableStateOf(false) }
    Window(
        onKeyEvent = {
            Direction.fromKey(it.key)?.let { direction.value = it }
            true
        },
        onCloseRequest = ::exitApplication,
        title = "snako",
    ) {
        LaunchedEffect(Unit) {
            window.addWindowListener(object : WindowListener {
                override fun windowOpened(p0: WindowEvent?) {}

                override fun windowClosing(p0: WindowEvent?) {}

                override fun windowClosed(p0: WindowEvent?) {}

                override fun windowIconified(p0: WindowEvent?) {}

                override fun windowDeiconified(p0: WindowEvent?) {}

                override fun windowActivated(p0: WindowEvent?) {
                    isFocused = true
                }

                override fun windowDeactivated(p0: WindowEvent?) {
                    isFocused = false
                }

            })
        }
        App(isScreenActive = isFocused, direction = direction.value)
    }
}