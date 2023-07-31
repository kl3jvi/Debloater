package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Gite
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.adb.AdbService.deviceList
import data.adb.AdbService.getDeviceModel
import data.adb.AdbService.getUsers
import java.awt.Desktop
import java.net.URI

@Composable
fun TopBar(
    update: (String) -> Unit,
    updateUser: (String) -> Unit,
    reboot: (String) -> Unit
) {
    TopAppBar {
        val devices = deviceList().map { it.getDeviceModel() }
        var selectedDevice by remember { mutableStateOf(devices[0]) }
        var deviceExpanded by remember { mutableStateOf(false) }

        val users = getUsers(selectedDevice.second)
        var selectedUser by remember { mutableStateOf(users[0]) }
        var userExpanded by remember { mutableStateOf(false) }

        Button(
            onClick = { reboot(selectedDevice.second) },
            border = BorderStroke(1.dp, Color.White),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0x50CBE6FF)),
            elevation = ButtonDefaults.elevation(defaultElevation = 10.dp),
            modifier = Modifier.padding(start = 10.dp)
        ) {
            Icon(imageVector = Icons.Default.RestartAlt, contentDescription = "Person Icon")
            Text(text = "Reboot")
        }

        Row(
            modifier = Modifier.padding(start = 10.dp)
        ) {
            Column {
                Button(
                    onClick = {
                        deviceExpanded = true
                        update(selectedDevice.second)
                        updateUser(selectedUser)
                    },
                    border = BorderStroke(1.dp, Color.White),
                    elevation = ButtonDefaults.elevation(defaultElevation = 10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(selectedDevice.second)
                        Icon(Icons.Filled.ArrowDropDown, contentDescription = "Open dropdown")
                    }
                }

                DropdownMenu(
                    expanded = deviceExpanded,
                    onDismissRequest = { deviceExpanded = false }
                ) {
                    devices.forEach { label ->
                        DropdownMenuItem(onClick = {
                            selectedDevice = label
                            deviceExpanded = false
                            // AdbService.rebootDevice(selectedOption)
                        }) {
                            Text(text = label.second)
                        }
                    }
                }
            }

            Column {
                Button(
                    onClick = {
                        userExpanded = true
                        updateUser(selectedUser)
                    },
                    border = BorderStroke(1.dp, Color.White),
                    elevation = ButtonDefaults.elevation(defaultElevation = 10.dp),
                    modifier = Modifier.padding(start = 10.dp)

                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(selectedUser)
                        Icon(Icons.Filled.ArrowDropDown, contentDescription = "Open dropdown")
                    }
                }

                DropdownMenu(
                    expanded = userExpanded,
                    onDismissRequest = { userExpanded = false }
                ) {
                    users.forEach { label ->
                        DropdownMenuItem(onClick = {
                            selectedUser = label
                            userExpanded = false
                        }) {
                            Text(text = label)
                        }
                    }
                }
            }
        }
        // This spacer will push the version number to the end of the AppBar.
        Spacer(modifier = Modifier.weight(1f))

        // Github icon button
        IconButton(
            onClick = {
                Desktop.getDesktop().browse(URI("https://github.com/kl3jvi/Debloater"))
            }
        ) {
            Icon(imageVector = Icons.Default.Gite, contentDescription = "Person Icon")
        }

        // The version number
        Text(text = "v0.0.1", modifier = Modifier.padding(end = 10.dp))
    }
}
