package app.helium.heliumapp

import android.Manifest
import androidx.activity.result.ActivityResult
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import app.helium.heliumapp.data.gattble.presentation.HeliumViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import app.helium.heliumapp.ui.theme.HeliumTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navHostController: NavHostController
    private val viewModel: HeliumViewModel by viewModels()
    private var isBluetootPermissionGranted = false
    @Inject
    lateinit var bluetoothAdapter: BluetoothAdapter

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
        setContent {

            HeliumTheme {
                navHostController = rememberNavController()
                DestinationsNavHost(navGraph = NavGraphs.root)

            }
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onStart() {
        super.onStart()
        // TODO-INFO-BLUETOOTH-SCANNING-STARTS
        // TODO-FIXME-BRINGBACKMAYBE scanBluetoothPermission()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }



    fun scanBluetoothPermission() {
        // TODO-FIXME-CLEANUP-bluetoothAdapter should be injected via DI @Inject
        val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

        if (bluetoothAdapter == null) {
            Toast.makeText(
                this,
                "Device doesn't support Bluetooth",
                Toast.LENGTH_LONG
            ).show()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                bluetoothPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT)
            } else {
                bluetoothPermissionLauncher.launch(Manifest.permission.BLUETOOTH_ADMIN)
            }
        }
    }

    private val bluetoothPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // TODO-FIXME-CLEANUP-bluetoothAdapter should be injected via DI @Inject
            val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
            val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

            if (bluetoothAdapter?.isEnabled == false) {
                val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                bluetoothActivityResultLauncher.launch(enableBluetoothIntent)
            } else {
                bluetoothScanSuccessful()
            }
        } else {
            isBluetootPermissionGranted = false
        }
    }

    private fun bluetoothScanSuccessful() {
        Toast.makeText(this, "Bluetooth connected", Toast.LENGTH_LONG).show()
    }

    private val bluetoothActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            bluetoothScanSuccessful()
        }

    }

}
