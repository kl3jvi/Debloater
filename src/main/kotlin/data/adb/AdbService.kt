package data.adb

import dadb.Dadb

object AdbService {
    private val selectedDevice = Dadb.discover().toString()

    fun deviceList(): List<Dadb> {
        return Dadb.list()
    }

    fun getPackages(): List<String> {
        return deviceList()
            .firstOrNull { it.toString() == selectedDevice }
            ?.shell("pm list packages | cut -f 2 -d \":\"\n")
            ?.allOutput
            ?.parsePackages()
            ?: emptyList()
    }

    fun uninstallSelected(vararg appList: String) {
        val currentDevice = deviceList().firstOrNull { it.toString() == selectedDevice }
        appList.map { currentDevice?.uninstall(it) }
    }

    fun isEmulator(): Boolean {
        val currentDevice = deviceList().firstOrNull { it.toString() == selectedDevice }
        val hardware = currentDevice?.shell("getprop ro.hardware")?.allOutput?.trim()
        return hardware == "goldfish" || hardware == "ranchu"
    }

    private fun String.parsePackages(): List<String> {
        return this.split("\n")
            .map(String::trim)
            .filter(String::isNotEmpty)
    }
}
