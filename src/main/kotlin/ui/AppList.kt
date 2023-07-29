package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AppList() {
    var searchText by remember { mutableStateOf("") }

    val items = listOf(
        "com.kl3jvi.animity",
        "com.roblox.client",
        "com.outfit7.mytalkingtomfriends",
        "com.instagram.android",
        "com.microsoft.outlooklite",
        "com.easilydo.mail",
        "al.myvodafone.android",
        "com.vyroai.aiart",
        "com.example.package5",
        "com.example.package1",
        "com.example.package2",
        "com.example.package3",
        "com.example.package4",
        "com.example.package5"
    )

    val filteredItems = items.filter { it.contains(searchText, ignoreCase = true) }

    Column {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search packages") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        LazyColumn {
            if (filteredItems.isNotEmpty()) {
                items(filteredItems) { item ->
                    MyAppRow(item, false, {}, {})
                }
            } else {
                item {
                    Text(
                        text = "No app found with this package name.",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.Red
                    )
                }
            }
        }
    }
}


@Composable
fun MyAppRow(
    appName: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onUninstall: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colors.onPrimary)
            .border(1.dp, Color.White),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )

        Text(
            text = appName,
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f) // This makes the text expand as much as possible
        )

        Button(onClick = onUninstall) {
            Text("Uninstall")
        }
    }
}
