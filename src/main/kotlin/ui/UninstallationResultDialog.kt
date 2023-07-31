package ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UninstallationResultDialog(dialogState: Pair<Boolean, String>, dismissAction: () -> Unit) {
    if (dialogState.first) {
        AlertDialog(
            onDismissRequest = dismissAction,
            title = { Text(modifier = Modifier.fillMaxWidth(), text = "Uninstallation Result") },
            text = { Text(dialogState.second) },
            buttons = {
                Button(onClick = dismissAction) {
                    Text("Ok")
                }
            }
        )
    }
}
