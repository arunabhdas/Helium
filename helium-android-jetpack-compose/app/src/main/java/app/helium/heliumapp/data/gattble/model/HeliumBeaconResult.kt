package app.helium.heliumapp.data.gattble.model

data class HeliumBeaconResult(
    val proximity: Float,
    val timestamp: Float,
    val rssi: Float,
    val region: String,
    val accuracy: Float,
    val major: Float,
    val uuid: String,
    val minor: Float,
    val connectionState: ConnectionState
)

