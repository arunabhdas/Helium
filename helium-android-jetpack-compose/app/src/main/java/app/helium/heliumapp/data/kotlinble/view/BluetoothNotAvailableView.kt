package app.helium.heliumapp.data.kotlinble.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BluetoothDisabled
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import app.helium.heliumapp.R
import app.helium.heliumapp.ui.theme.HeliumTheme


@Composable
internal fun BluetoothNotAvailableView() {
    WarningView(
        imageVector = Icons.Default.BluetoothDisabled,
        title = stringResource(id = R.string.bluetooth_not_available_title),
        hint = stringResource(id = R.string.bluetooth_not_available_info),
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    )
}

@Preview
@Composable
private fun BluetoothNotAvailableView_Preview() {
    HeliumTheme {
        BluetoothNotAvailableView()
    }
}
