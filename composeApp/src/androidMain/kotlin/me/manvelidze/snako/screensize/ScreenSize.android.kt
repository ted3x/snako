package me.manvelidze.snako.screensize

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
actual fun getScreenWidth() = LocalConfiguration.current.screenWidthDp.dp

@Composable
actual fun getScreenHeight(): Dp = LocalConfiguration.current.screenHeightDp.dp

@Composable
actual fun Dp.toPx() = with(LocalDensity.current) { this@toPx.toPx() }