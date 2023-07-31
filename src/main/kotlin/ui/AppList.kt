package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.adb.AdbService.getPackages
import data.adb.AdbService.uninstallSelected

@Composable
fun AppList(selectedDevice: MutableState<String>) {
    var searchText by remember { mutableStateOf("") }
    val items = getPackages(selectedDevice.value)
    val checkedItems = remember {
        mutableStateMapOf<String, Boolean>().also { map ->
            items.forEach { appName ->
                map[appName] = false
            }
        }
    }

    var dialogState by rememberSaveable { mutableStateOf(false to "") }
    val confirmDialogState = remember { mutableStateOf(false) }

    val uninstallAndSetDialogState: () -> Unit = {
        val uninstallResults = uninstallSelected(
            *checkedItems.filterValues {
                it
            }.keys.toTypedArray()
        )
        val failedUninstalls = uninstallResults.filter { !it.second }.map { it.first }
        val dialogMessage = if (failedUninstalls.isEmpty()) {
            "All selected apps were successfully uninstalled."
        } else {
            "Apps couldn't be uninstalled: ${failedUninstalls.joinToString(", ")}. Please check the device's permissions or state."
        }
        dialogState = true to dialogMessage
    }

    UninstallationResultDialog(dialogState) { dialogState = false to "" }
    ConfirmationDialog(confirmDialogState) {
        confirmDialogState.value = false
        uninstallAndSetDialogState()
    }

    val filteredItems = items
        .filter { it.contains(searchText, ignoreCase = true) }

    Column {
        PackageSearchBar(searchText) { searchText = it }
        PackageList(filteredItems, checkedItems) { confirmDialogState.value = true }
    }
}

@Composable
fun ControlButtonsRow(
    onSelectAll: () -> Unit,
    onUnselectAll: () -> Unit,
    onUninstallSelection: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp)
            .background(MaterialTheme.colors.onPrimary)
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onSelectAll,
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text("Select All")
        }

        Button(
            onClick = onUnselectAll,
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text("Unselect All")
        }

        Button(
            onClick = onUninstallSelection,
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text("Uninstall Selection")
        }
    }
}
