// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dadb.Dadb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        TopBar(::reboot)

    }
}

fun reboot(selectedDevice: String) {
    CoroutineScope(Dispatchers.IO).launch {
        Dadb.list().forEach {
            it.root()
        }

    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Debloater",
    ) {
        App()
    }
}
