package data.adb

import dadb.AdbShellResponse
import dadb.Dadb
import java.util.regex.Pattern

object AdbService {
    private val selectedDevice = Dadb.discover().getDeviceModel()

    fun deviceList(): List<Dadb> {
        return Dadb.list()
    }

    fun getPackages(value: String): List<String> {
        return deviceList().firstOrNull { it.getDeviceModel().second == value }
            ?.shell("pm list packages | cut -f 2 -d \":\"\n")?.allOutput?.parsePackages() ?: emptyList()
    }

    fun uninstallSelected(vararg appList: String): List<Boolean> {
        val currentDevice = deviceList().firstOrNull { it.getDeviceModel() == selectedDevice }
        return appList.map {
            runCatching { currentDevice?.uninstall(packageName = it) }
                .fold(
                    onSuccess = { true },
                    onFailure = { false }
                )
        }
    }

    fun isEmulator(): Boolean {
        val currentDevice = deviceList().firstOrNull { it.getDeviceModel() == selectedDevice }
        val hardware = currentDevice?.shell("getprop ro.hardware")?.allOutput?.trim()
        return hardware == "goldfish" || hardware == "ranchu"
    }

    private fun String.parsePackages(): List<String> {
        return this.split("\n").map(String::trim).filter(String::isNotEmpty)
    }

    /**
     * Get the model of the selected device.
     *
     * @return The model of the selected device.
     */
    fun Dadb?.getDeviceModel(): Pair<String, String> {
        return Pair(
            this?.toString().orEmpty(), this?.shell("getprop ro.product.model")?.allOutput?.trim() ?: "Unknown device"
        )
    }

    fun getUsers(device: String): List<String> {
        val currentDevice = deviceList().firstOrNull { it.getDeviceModel().second == device }
        val output = currentDevice?.shell("pm list users")?.allOutput ?: ""

        val pattern = Pattern.compile("UserInfo\\{(?<id>\\d+):(?<name>\\w+):(?<flags>\\w+)\\}")
        val matcher = pattern.matcher(output)

        val users = mutableListOf<String>()
        while (matcher.find()) {
            users.add(matcher.group("name"))
        }
        return users
    }

    fun rebootDevice(deviceSerialNumber: String): AdbShellResponse? {
        val currentDevice = deviceList().firstOrNull { it.getDeviceModel().second == deviceSerialNumber }
        currentDevice?.shell("adb shell am broadcast -a android.intent.action.BOOT_COMPLETED")?.allOutput
        return currentDevice?.shell("adb shell am broadcast -a android.intent.action.BOOT_COMPLETED")
    }
}
