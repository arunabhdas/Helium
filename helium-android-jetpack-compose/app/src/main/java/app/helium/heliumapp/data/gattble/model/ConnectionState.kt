package app.helium.heliumapp.data.gattble.model

sealed interface ConnectionState {
    object Connected: ConnectionState
    object Disconnected: ConnectionState
    object Uninitialized: ConnectionState
    object CurrentlyInitializing: ConnectionState
}