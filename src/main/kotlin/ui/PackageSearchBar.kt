package ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PackageSearchBar(searchText: String, onValueChange: (String) -> Unit) {
    TextField(
        value = searchText,
        onValueChange = onValueChange,
        label = { Text("Search packages") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(8.dp)
    )
}
