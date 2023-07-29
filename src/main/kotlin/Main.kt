// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.AppList
import ui.TopBar

@Composable
@Preview
fun App() {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(
            primary = Color(0xFF22577A),
            primaryVariant = Color(0xFFCBE6FF),
            onPrimary = Color.White,
            secondary = Color(0xFF50606F),
            onSecondary = Color.White,
            background = Color(0xFFFCFCFF),
            onBackground = Color(0xFF1A1C1E)
        )
    ) {
        Column {
            TopBar {
//                AdbService.rebootDevice(it)
            }
            AppList()
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Debloater"
    ) {
        App()
    }
}
