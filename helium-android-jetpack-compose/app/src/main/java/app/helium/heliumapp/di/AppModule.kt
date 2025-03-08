package app.helium.heliumapp.di

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import app.helium.heliumapp.data.gattble.manager.HeliumBeaconReceiveManager
import app.helium.heliumapp.data.gattble.manager.HeliumBeaconBleReceiveManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import no.nordicsemi.android.kotlin.ble.scanner.BleScanner
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideBluetoothAdapter(
        @ApplicationContext context: Context
    ): BluetoothAdapter {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        return bluetoothManager.adapter
    }

    @Provides
    @Singleton
    fun provideHeliumBeaconReceiveManager(
        @ApplicationContext context: Context,
        bluetoothAdapter: BluetoothAdapter
    ): HeliumBeaconReceiveManager {
        return HeliumBeaconBleReceiveManager(bluetoothAdapter, context)
    }

    @Provides
    fun provideBleScanner(
        @ApplicationContext
        context: Context
    ): BleScanner {
        return BleScanner(context)
    }

}