package app.helium.heliumapp.data.blescanner

object HeliumBeaconRepository {
    val heliumBeacons = listOf(
        HeliumBeacon(
            1,
            "Test1",
            "UUID1",
            "12345"
        ),
        HeliumBeacon(
            2,
            "Test2",
            "UUID2",
            "12345"
        ),
        HeliumBeacon(
            3,
            "Test3",
            "UUID3",
            "12345"
        ),
        HeliumBeacon(
            4,
            "Test4",
            "UUID4",
            "12345"
        )

    )

    fun getHeliumBeacons(id: Int) = heliumBeacons.firstOrNull { it.id == id }
}


