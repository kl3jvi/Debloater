package data.adb

import dadb.Dadb
import dadb.adbserver.AdbServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

object AdbService {
    private var dadUnit: Dadb? = null
    private val scope = CoroutineScope(Dispatchers.IO)


    fun deviceList(): List<Dadb> {
        return emptyList()
    }

    fun selectDevice(deviceSerialNumber: String) {
        try {
            dadUnit = AdbServer.createDadb(
                adbServerHost = "localhost",
                adbServerPort = 5037,
                deviceQuery = "host:transport:$deviceSerialNumber"
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(Exception::class)
    fun rebootDevice(device: String) {
        scope.launch {
            deviceList().firstOrNull {
                it.toString() == device
            }?.root() ?: throw Exception("Device not found!")
        }
    }
}