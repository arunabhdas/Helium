package app.helium.heliumapp.data.kotlinble.view

import app.helium.heliumapp.R
import app.helium.heliumapp.ui.theme.HeliumTheme

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.BluetoothSearching
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun ScanErrorView(
    error: Int,
) {
    WarningView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        imageVector = Icons.AutoMirrored.Filled.BluetoothSearching,
        title = stringResource(id = R.string.scanner_error),
        hint = stringResource(id = R.string.scan_failed, error),
    )
}

@Preview
@Composable
private fun ErrorSectionPreview() {
    HeliumTheme {
        ScanErrorView(3)
    }
}