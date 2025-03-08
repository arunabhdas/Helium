package app.helium.heliumapp.data.blescanner

/*
data class HeliumBeacon (
    val id: Int,
    val uuid: String?,
    val major: Int?,
    val minor: Int?,

)
*/
data class HeliumBeacon(
    val id: Int,
    val name: String,
    val description: String,
    val uuid: String,
)