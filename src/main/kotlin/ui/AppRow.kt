package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.Descriptions.getDescription
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppRow(
    appName: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onUninstall: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    var appDescription by remember { mutableStateOf("") }

    val onRowClick: () -> Unit = {
        coroutineScope.launch(Dispatchers.IO) {
            appDescription = getDescription(appName)
            showDialog = true
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Description") },
            text = { Text(appDescription) },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Close")
                }
            }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFd0d7de))
            .background(MaterialTheme.colors.onPrimary)
            .wrapContentHeight()
            .clickable(onClick = onRowClick),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )

        Text(
            text = appName,
            fontSize = 13.sp,
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
