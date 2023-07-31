package ui

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UninstallationResultDialog(dialogState: Pair<Boolean, String>, dismissAction: () -> Unit) {
    if (dialogState.first) {
        AlertDialog(
            onDismissRequest = dismissAction,
            title = { Text("Uninstallation Result") },
            text = { Text(dialogState.second) },
            buttons = {
                Button(onClick = dismissAction) {
                    Text("Ok")
                }
            }
        )
    }
}
