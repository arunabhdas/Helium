package app.helium.heliumapp.data.kotlinble.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import javax.inject.Inject
import app.helium.heliumapp.data.kotlinble.bluetooth.BluetoothStateManager
import app.helium.heliumapp.data.kotlinble.bluetooth.LocationStateManager
import app.helium.heliumapp.data.kotlinble.util.BlePermissionNotAvailableReason
import app.helium.heliumapp.data.kotlinble.util.BlePermissionState
import kotlinx.coroutines.flow.stateIn


@HiltViewModel
internal class PermissionViewModel @Inject internal constructor(
    private val bluetoothManager: BluetoothStateManager,
    private val locationManager: LocationStateManager,
) : ViewModel() {
    val bluetoothState = bluetoothManager.bluetoothState()
        .stateIn(
            viewModelScope, SharingStarted.Lazily,
            BlePermissionState.NotAvailable(BlePermissionNotAvailableReason.NOT_AVAILABLE)
        )

    val locationPermission = locationManager.locationState()
        .stateIn(
            viewModelScope, SharingStarted.Lazily,
            BlePermissionState.NotAvailable(BlePermissionNotAvailableReason.NOT_AVAILABLE)
        )

    fun refreshBluetoothPermission() {
        bluetoothManager.refreshPermission()
    }

    fun refreshLocationPermission() {
        locationManager.refreshPermission()
    }

    fun markLocationPermissionRequested() {
        locationManager.markLocationPermissionRequested()
    }

    fun markBluetoothPermissionRequested() {
        bluetoothManager.markBluetoothPermissionRequested()
    }

    fun isBluetoothScanPermissionDeniedForever(context: Context): Boolean {
        return bluetoothManager.isBluetoothScanPermissionDeniedForever(context)
    }

    fun isLocationPermissionDeniedForever(context: Context): Boolean {
        return locationManager.isLocationPermissionDeniedForever(context)
    }
}
