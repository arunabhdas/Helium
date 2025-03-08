package app.helium.heliumapp.data.gattble.manager

import app.helium.heliumapp.data.gattble.model.HeliumBeaconResult
import app.helium.heliumapp.data.gattble.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow

interface HeliumBeaconReceiveManager {
    val data: MutableSharedFlow<Resource<HeliumBeaconResult>>

    fun reconnect()

    fun disconnect()

    fun startReceiving()

    fun closeConnection()

}