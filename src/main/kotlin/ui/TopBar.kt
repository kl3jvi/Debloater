package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dadb.Dadb

@Composable
fun TopBar(reboot: (String) -> Unit) {
    TopAppBar {
        val options = Dadb.list().map(Any::toString)

        var expanded by remember { mutableStateOf(false) }
        var selectedOption by remember { mutableStateOf(options[0]) }

        Button(
            onClick = { reboot(selectedOption) },
            border = BorderStroke(1.dp, Color.White),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0x50CBE6FF)),
            elevation = ButtonDefaults.elevation(defaultElevation = 10.dp),
            modifier = Modifier.padding(start = 10.dp)
        ) {
            Text(text = "Reboot")
        }

        Column(
            modifier = Modifier.padding(start = 10.dp),
        ) {
            Button(
                onClick = { expanded = true },
                border = BorderStroke(1.dp, Color.White),
                elevation = ButtonDefaults.elevation(defaultElevation = 10.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(selectedOption)
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Open dropdown")
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEach { label ->
                    DropdownMenuItem(onClick = {
                        selectedOption = label
                        expanded = false
                    }) {
                        Text(text = label)
                    }
                }
            }
        }
        // This spacer will push the version number to the end of the AppBar.
        Spacer(modifier = Modifier.weight(1f))

        // The version number
        Text(text = "v0.0.1", modifier = Modifier.padding(end = 10.dp))
    }
}