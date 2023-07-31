package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
