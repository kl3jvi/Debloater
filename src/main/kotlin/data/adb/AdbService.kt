package data.adb

import dadb.Dadb

object AdbService {

    fun deviceList(): List<Dadb> {
        return Dadb.list()
    }

    fun getPackages(device: String): List<String> {
        return deviceList()
            .firstOrNull { it.toString() == device }
            ?.shell("pm list packages | cut -f 2 -d \":\"\n")
            ?.allOutput
            ?.parsePackages()
            ?: emptyList()
    }

    private fun String.parsePackages(): List<String> {
        return this.split("\n")
            .map(String::trim)
            .filter(String::isNotEmpty)
    }
}