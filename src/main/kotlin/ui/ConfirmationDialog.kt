package ui

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ConfirmationDialog(confirmDialogState: MutableState<Boolean>, confirmAction: () -> Unit) {
    if (confirmDialogState.value) {
        AlertDialog(
            onDismissRequest = { confirmDialogState.value = false },
            title = { Text("Confirm Uninstallation") },
            text = { Text("Do you really want to uninstall the selected apps?") },
            buttons = {
                Button(onClick = { confirmDialogState.value = false }) {
                    Text("Cancel")
                }
                Button(onClick = confirmAction) {
                    Text("Confirm")
                }
            }
        )
    }
}
