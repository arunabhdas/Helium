package app.helium.heliumapp.data.gattble.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.helium.heliumapp.data.gattble.model.ConnectionState
import app.helium.heliumapp.data.gattble.manager.HeliumBeaconReceiveManager
import app.helium.heliumapp.data.gattble.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeliumBeaconViewModel @Inject constructor(
    private val heliumBeaconReceiveManager: HeliumBeaconReceiveManager
): ViewModel(){
    var initializingMessage by mutableStateOf<String?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var proximity by mutableStateOf(0f)
        private set

    var rssi by mutableStateOf(0f)
        private set

    var connectionState by mutableStateOf<ConnectionState>(ConnectionState.Uninitialized)

    private fun subscribeToChanges() {
        viewModelScope.launch {
            heliumBeaconReceiveManager.data.collect { result ->
                when(result) {
                    is Resource.Success -> {
                        connectionState = result.data.connectionState
                        proximity = result.data.proximity
                        rssi = result.data.rssi
                    }

                    is Resource.Loading -> {
                        initializingMessage = result.message
                        connectionState = ConnectionState.CurrentlyInitializing
                    }

                    is Resource.Error -> {

                    }
                }
            }
        }
    }

    fun disconnect() {
        heliumBeaconReceiveManager.disconnect()
    }

    fun reconnect() {
        heliumBeaconReceiveManager.reconnect()
    }

    fun initializeConnection() {
        errorMessage = null
        subscribeToChanges()
        heliumBeaconReceiveManager.startReceiving()
    }

    override fun onCleared() {
        super.onCleared()
        heliumBeaconReceiveManager.closeConnection()
    }
}