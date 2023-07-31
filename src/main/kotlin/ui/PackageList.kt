package ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun PackageList(
    filteredItems: List<String>,
    checkedItems: MutableMap<String, Boolean>,
    onUninstall: () -> Unit
) {
    LazyColumn {
        if (filteredItems.isNotEmpty()) {
            items(filteredItems) { item ->
                AppRow(
                    appName = item,
                    checked = checkedItems[item] ?: false,
                    onCheckedChange = { isChecked ->
                        checkedItems[item] = isChecked
                    },
                    onUninstall = onUninstall
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
