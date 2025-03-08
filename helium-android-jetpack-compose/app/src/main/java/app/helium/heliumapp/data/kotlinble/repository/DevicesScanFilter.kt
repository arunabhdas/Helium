package app.helium.heliumapp.data.kotlinble.repository

data class DevicesScanFilter(
    val filterUuidRequired: Boolean?,
    val filterNearbyOnly: Boolean,
    val filterWithNames: Boolean
)
