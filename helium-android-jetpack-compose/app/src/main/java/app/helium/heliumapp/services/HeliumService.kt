package app.helium.heliumapp.services

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.ParcelUuid
import android.text.TextUtils
import app.helium.heliumapp.R
import app.helium.heliumapp.data.blescanner.AdRecord
import app.helium.heliumapp.data.blescanner.Beacon
import app.helium.heliumapp.data.blescanner.Utils
import app.helium.heliumapp.permissions.PermUtils
import timber.log.Timber
import java.io.UnsupportedEncodingException
import java.util.Arrays


class HeliumService: Service() {
    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var bluetoothLeScanner: BluetoothLeScanner

    val eddystoneServiceId: ParcelUuid = ParcelUuid.fromString("0000FEAA-0000-1000-8000-00805F9B34FB")
    var beaconSet: HashSet<Beacon> = HashSet()
    private var beacons: List<Beacon> = listOf()
    private var selectedBeaconType: Int = 0
    val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val scanRecord = result.scanRecord
            val beacon = Beacon(result.device.address)
            // TODO-FIXME-IMPROVE beacon.manufacturer = result.device.name
            beacon.rssi = result.rssi
            if (scanRecord != null) {
                val serviceUuids = scanRecord.serviceUuids
                // printScanRecord(scanRecord.bytes)
                val txPower = getTxPowerLevel(scanRecord.bytes)
                val iBeaconManufactureData = scanRecord.getManufacturerSpecificData(0X004c)
                if (serviceUuids != null && serviceUuids.size > 0 && serviceUuids.contains(
                        eddystoneServiceId
                    )
                ) {
                    val serviceData = scanRecord.getServiceData(eddystoneServiceId)
                    if (serviceData != null && serviceData.size > 18) {
                        val eddystoneUUID =
                            Utils.toHexString(Arrays.copyOfRange(serviceData, 2, 18))
                        val namespace = String(eddystoneUUID.toCharArray().sliceArray(0..19))
                        val instance = String(
                            eddystoneUUID.toCharArray()
                                .sliceArray(20 until eddystoneUUID.toCharArray().size)
                        )
                        beacon.type = Beacon.beaconType.eddystoneUID
                        beacon.namespace = namespace
                        beacon.instance = instance

                        Timber.d( "Namespace:$namespace Instance:$instance")
                    }
                }
                if (iBeaconManufactureData != null && iBeaconManufactureData.size >= 23) {
                    val iBeaconUUID = Utils.toHexString(iBeaconManufactureData.copyOfRange(2, 18))
                    val major = Integer.parseInt(
                        Utils.toHexString(
                            iBeaconManufactureData.copyOfRange(
                                18,
                                20
                            )
                        ), 16
                    )
                    val minor = Integer.parseInt(
                        Utils.toHexString(
                            iBeaconManufactureData.copyOfRange(
                                20,
                                22
                            )
                        ), 16
                    )

                    beacon.type = Beacon.beaconType.iBeacon
                    beacon.uuid = iBeaconUUID
                    beacon.major = major
                    beacon.minor = minor
                    beacon.txPower = txPower
                    beacon.proximity = calculateProximity(
                        txPower = txPower,
                        rssi = beacon.rssi!!
                    )

                    Timber.d( "HeliumService - " +
                            "iBeaconUUID: $iBeaconUUID " +
                            "major:$major " +
                            "minor:$minor " +
                            "rssi : ${beacon.rssi} + " +
                            "proximity: ${beacon.proximity}")
                }
            }
            beaconSet.add(beacon)
        }

        override fun onScanFailed(errorCode: Int) {
            // Handle scan failure, e.g., log an error
            Timber.d( "Scan failed with error code: $errorCode")
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
        return null
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "ibeacon_scanning_channel",
                "Beacon Scanning Service",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        bluetoothManager = this.getSystemService(BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager.adapter
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.action) {
            Actions.START.toString() -> startHeliumService()

            Actions.STOP.toString() -> {
                stopHeliumService()
                stopSelf()
            }
        }
        return super.onStartCommand(intent, flags, startId)

    }

    private fun stopHeliumService() {
        Timber.d("Stop HeliumService")
        stopBluetoothScanning(bluetoothLeScanner, leScanCallback)
    }



    private fun startHeliumService() {
        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, "ibeacon_scanning_channel")
        } else {
            Notification.Builder(this)
        }.setSmallIcon(R.drawable.appicon)
            .setContentTitle("Helium is active")
            .setContentText("This facility is secured by Helium")
            .build()

        startForeground(1, notification)
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            Timber.e("HeliumService - Device doesn't support Bluetooth")
            throw Exception("Device doesn't support Bluetooth")
        } else {
            if (isBluetoothEnabled()) {
                bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
                startBluetoothScanning(
                    context = this,
                    btScanner = bluetoothLeScanner,
                    beacons = beacons,
                    selectedBeaconType = selectedBeaconType,
                    scanCallback = leScanCallback
                )
            }
        }
    }




    enum class Actions {
        START, STOP

    }

    /**
     * Determine whether bluetooth is enabled or not
     *
     * @return true if bluetooth is enabled, false otherwise
     */
    fun isBluetoothEnabled(): Boolean {
        return bluetoothAdapter.isEnabled
    }

    @SuppressLint("MissingPermission")
    fun startBluetoothScanning(
        context: Context,
        btScanner: BluetoothLeScanner?,
        beacons: List<Beacon>,
        selectedBeaconType: Int,
        scanCallback: ScanCallback
    ) {
        val filters = mutableListOf<ScanFilter>()
        val settings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .build()

        // Define the Eddystone Service UUID to filter
        val eddystoneServiceId = ParcelUuid.fromString("0000FEAA-0000-1000-8000-00805F9B34FB")

        // Add filters based on selected beacon type
        when (selectedBeaconType) {
            // Example for Eddystone; adjust as needed for other types
            1 -> filters.add(ScanFilter.Builder().setServiceUuid(eddystoneServiceId).build())
            // Add other cases as necessary
        }

        if (!PermUtils.hasBluetoothPermissions(context)) {
            // Handle missing permissions
            Timber.d("Helium - Missing Bluetooth Permissions")
            return
        }

        // 2. Check Bluetooth Availability
        val btManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val btAdapter = btManager.adapter
        if (btAdapter == null || !btAdapter.isEnabled) {
            // Handle Bluetooth not available or disabled
            Timber.d("Helium - Bluetooth Adapter is null or not enabled")
            return
        }

        // 3. Requirements Checker (Implement this based on your app's specific needs)
        // This could include checking for specific hardware capabilities, etc.

        // 4. Reduce Callback Registrations
        // (Implement if you have additional features that register more callbacks)


        btScanner?.startScan(scanCallback)
    }

    @SuppressLint("MissingPermission")
    fun stopBluetoothScanning(
        btScanner: BluetoothLeScanner?,
        scanCallback: ScanCallback
    ) {
        Timber.d("Helium - stopBluetoothScanning stop beacon scan")
        // Implement logic to stop Bluetooth scanning
        btScanner?.stopScan(scanCallback)
    }

    fun ByteArrayToString(ba: ByteArray): String {
        val hex = StringBuilder(ba.size * 2)
        for (b in ba) hex.append("$b ")
        return hex.toString()
    }

    fun printScanRecord(scanRecord: ByteArray) {
        // Simply print all raw bytes
        try {
            val decodedRecord = String(scanRecord, charset("UTF-8"))
            Timber.d( "HeliumService - decoded String : " + ByteArrayToString(scanRecord))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        // Parse data bytes into individual records
        val records: List<AdRecord?> = AdRecord.parseScanRecord(scanRecord)


        // Print individual records
        if (records.size == 0) {
            Timber.d("HeliumService - Scan Record Empty")
        } else {
            Timber.d("HeliumService - Scan Record: " + TextUtils.join(",", records))
        }
    }



    object AssignedNumbers {
        const val TXPOWER: Byte = 0x0A
    }

    fun getTxPowerLevel(scanRecord: ByteArray): Int {
        // Check for BLE 4.0 TX power
        val pos: Int = findCodeInBuffer(scanRecord, AssignedNumbers.TXPOWER)
        return if (pos > 0) {
            Integer.valueOf(scanRecord[pos].toInt())
        } else 1
    }

    private fun findCodeInBuffer(buffer: ByteArray, code: Byte): Int {
        val length = buffer.size
        var i = 0
        while (i < length - 2) {
            val len = buffer[i].toInt()
            if (len < 0) {
                return -1
            }
            if (i + len >= length) {
                return -1
            }
            val tcode = buffer[i + 1]
            if (tcode == code) {
                return i + 2
            }
            i += len + 1
        }
        return -1
    }

    private fun calculateProximity(txPower: Int, rssi: Int): Double {
        if (rssi == 0) {
            return -1.0 // if we cannot determine accuracy, return -1.
        }
        val ratio = rssi * 1.0 / txPower
        return if (ratio < 1.0) {
            Math.pow(ratio, 10.0)
        } else {
            0.89976 * Math.pow(ratio, 7.7095) + 0.111
        }
    }


}