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
import data.adb.AdbService.getPackages
import data.adb.AdbService.uninstallSelected

enum class SortOrder { Ascending, Descending, None }

@Composable
fun AppList() {
    var searchText by remember { mutableStateOf("") }
    val sortOrder by remember { mutableStateOf(SortOrder.Ascending) }
    val expanded = remember { mutableStateOf(false) }

    val items = getPackages()
    val checkedItems = remember {
        mutableStateMapOf<String, Boolean>().also { map ->
            items.forEach { appName ->
                map[appName] = false
            }
        }
    }

    val filteredItems = items
        .filter { it.contains(searchText, ignoreCase = true) }
        .sortedWith(compareBy<String> { it }.let { if (sortOrder == SortOrder.Descending) it.reversed() else it })

    Column {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search packages") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(8.dp)
        )

        LazyColumn {
            if (filteredItems.isNotEmpty()) {
                items(filteredItems) { item ->
                    MyAppRow(
                        appName = item,
                        checked = checkedItems[item] ?: false,
                        onCheckedChange = { isChecked ->
                            checkedItems[item] = isChecked
                        },
                        onUninstall = {
                            uninstallSelected(*checkedItems.keys.toTypedArray())
                        }
                    )
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
            .padding(horizontal = 8.dp, vertical = 2.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFd0d7de))
            .background(MaterialTheme.colors.onPrimary),
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

        Button(
            onClick = onUninstall,
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text("Uninstall")
        }
    }
}
