package app.helium.heliumapp.ui.screens

import android.Manifest
import android.bluetooth.le.BluetoothLeScanner
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import app.helium.heliumapp.R
import app.helium.heliumapp.permissions.PermUtils
import app.helium.heliumapp.data.gattble.presentation.HeliumBeaconViewModel
import app.helium.heliumapp.ui.navigation.MockDestinationsNavigator
import app.helium.heliumapp.ui.navigation.Screen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import app.helium.heliumapp.data.gattble.model.ConnectionState
import app.helium.heliumapp.destinations.MainScreenBottomNavigationDestination
import app.helium.heliumapp.destinations.MainScreenDestination
import app.helium.heliumapp.permissions.RequestMultiplePermissions
import app.helium.heliumapp.services.HeliumService
import app.helium.heliumapp.ui.theme.TertiaryColor
import app.helium.heliumapp.ui.theme.ThemeUtils
import app.helium.heliumapp.ui.theme.TransparentColor
import app.helium.heliumapp.ui.theme.createGradientEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import timber.log.Timber

@RootNavGraph(start = true)
@Destination
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LandingScreen(
    // TODO-DEPRECATE navController: NavController,
    heliumBeaconViewModel: HeliumBeaconViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current
    val permissionState = rememberMultiplePermissionsState(
        PermUtils.permissions
    )

    val lifeCycleOwner = LocalLifecycleOwner.current

    val bleConnectionState = heliumBeaconViewModel.connectionState

    var bleScanner: BluetoothLeScanner? = null

    DisposableEffect(
        key1 = lifeCycleOwner,
        effect = {
            val observer = LifecycleEventObserver{_, event ->
                if (event == Lifecycle.Event.ON_START) {

                    if (
                        permissionState.allPermissionsGranted &&
                        bleConnectionState == ConnectionState.Disconnected
                        ) {


                        heliumBeaconViewModel.reconnect()
                    }
                }
                if (event == Lifecycle.Event.ON_STOP) {
                    if (bleConnectionState == ConnectionState.Connected) {
                        heliumBeaconViewModel.disconnect()
                    }
                }
            }

            lifeCycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifeCycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )

    LaunchedEffect(
        key1 = permissionState.allPermissionsGranted,
    ) {
        permissionState.launchMultiplePermissionRequest()
        if (permissionState.allPermissionsGranted) {
            Timber.d("Helium - Permissions were granted")
            Timber.d("Helium - Starting Service")
            // Start HeliumService
            // TODO-FIXME
            Intent(context, HeliumService::class.java).also {
                it.action = HeliumService.Actions.START.toString()
                context.startService(it)
            }
            if (bleConnectionState == ConnectionState.Uninitialized) {
                // TODO-FIXME-IMPROVE
                /* TODO-FIXME
                heliumBeaconViewModel.initializeConnection()
                */
                Toast.makeText(
                    context,
                    "Checking Permissions",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }



    LaunchedEffect(key1 = "LandingScreenAppeared") {
        Timber.d( "route: ${Screen.LandingScreen.route}")
    }
    var showExpandedText by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = createGradientEffect(
                    colors = ThemeUtils.GradientColors,
                    isVertical = true
                )
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 0.dp)
        ) {
            if (bleConnectionState == ConnectionState.CurrentlyInitializing) {
                Spacer(modifier = Modifier.height(30.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp),
                        color = TertiaryColor
                    )
                    if (heliumBeaconViewModel.initializingMessage != null) {
                        Text(
                            text = heliumBeaconViewModel.initializingMessage!!,
                            style = MaterialTheme.typography.h6.copy(color = Color.White),
                        )
                    }
                }
            } else if (!permissionState.allPermissionsGranted) {
                Spacer(modifier = Modifier.height(30.dp))
            } else if (heliumBeaconViewModel.errorMessage != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = heliumBeaconViewModel.errorMessage!!
                    )
                    Button(
                        onClick = {
                            Timber.d( "Helium - Debug Started")
                            if (permissionState.allPermissionsGranted) {
                                heliumBeaconViewModel.initializeConnection()
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .background(TransparentColor),
                        colors = ButtonDefaults.buttonColors(backgroundColor = TertiaryColor)
                    ) {
                        Text(text = "Initialize")
                    }
                }
            } else if (bleConnectionState == ConnectionState.Connected) {
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Proximity: ${heliumBeaconViewModel.proximity}",
                        style = MaterialTheme.typography.h3.copy(color = Color.White),
                    )
                    Text(
                        text = "RSSI : ${heliumBeaconViewModel.rssi}",
                        style = MaterialTheme.typography.h3.copy(color = Color.White),
                    )
                }

            }
            Spacer(modifier = Modifier.height(30.dp))
            Image(
                painter = painterResource(id = R.drawable.appicon),
                contentDescription = stringResource(id = R.string.helium_content_description),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(150.dp)
            )
            ClickableText(
                text = AnnotatedString("Helium"),
                style = MaterialTheme.typography.h5.copy(color = Color.White),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    showExpandedText = !showExpandedText
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedVisibility(visible = showExpandedText) {
                Text(
                    text = "Powered by Helium",
                    color = Color.White,
                    style = MaterialTheme.typography.body1.copy(color = Color.White),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {

                    navigator.navigate(
                        MainScreenDestination()
                    )
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(TransparentColor),
                colors = ButtonDefaults.buttonColors(backgroundColor = TertiaryColor)
            ) {
                Text(text = "Get Started")
            }
            Button(
                onClick = {

                    navigator.navigate(
                        MainScreenBottomNavigationDestination()
                    )
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(TransparentColor),
                colors = ButtonDefaults.buttonColors(backgroundColor = TertiaryColor)
            ) {
                Text(text = "Onboarding")
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                          navigator.navigate(
                              Screen.LoginScreen.route
                          )
                          /* TODO-FIXME-DEPRECATE
                          navController.navigate(
                              Screen.LoginScreen.route
                          )
                          */
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(TransparentColor),
                colors = ButtonDefaults.buttonColors(backgroundColor = TertiaryColor)
            ) {
                Text(text = "Enroll")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                     navigator.navigate(
                         Screen.RegistrationScreen.route
                     )
                    /* TODO-FIXME-DEPRECATE
                    navController.navigate(
                        Screen.RegistrationScreen.route
                    )
                    */
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(TransparentColor),
                colors = ButtonDefaults.buttonColors(backgroundColor = TertiaryColor)
            ) {
                Text(text = "Helium Contacts")
            }
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    Timber.d( "Helium - Beacon Scan Started")
                    /* TODO-FIXME-DEPRECATE
                    navigator.navigate(
                        Screen.DebugScreen.route
                    )
                    */
                    heliumBeaconViewModel.initializeConnection()

                    Toast.makeText(
                        context,
                        "Start Scan",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(TransparentColor),
                colors = ButtonDefaults.buttonColors(backgroundColor = TertiaryColor)
            ) {
                Text(text = "Start Scan")
            }
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    Timber.d( "Helium - Beacon Scan Started")

                    Toast.makeText(
                        context,
                        "Debug Initialized",
                        Toast.LENGTH_SHORT
                    ).show()

                    navigator.navigate(
                        Screen.ScannerScreen.route
                    )


                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(TransparentColor),
                colors = ButtonDefaults.buttonColors(backgroundColor = TertiaryColor)
            ) {
                Text(text = "Debug")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    Timber.d( "Helium - Beacon Scan Started")

                    Toast.makeText(
                        context,
                        "Test Initializing",
                        Toast.LENGTH_SHORT
                    ).show()
                    /* TODO-FIXME-BRING-BACK
                    navigator.navigate(
                        Screen.TestScreen.route
                    )
                    */
                    navigator.navigate(
                        Screen.ConnectScreen.route
                    )


                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(TransparentColor),
                colors = ButtonDefaults.buttonColors(backgroundColor = TertiaryColor)
            ) {
                Text(text = "Connect")
            }
            /* TODO-FIXME-CLEANUP
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Please go to the app settings and allow the missing permissions",
                style = MaterialTheme.typography.body1.copy(color = Color.White),
                modifier = Modifier.padding(10.dp),
                textAlign = TextAlign.Center
            )
            */


            RequestMultiplePermissions(
                permissions = listOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )

        }
    }

}



@Composable
@Preview
fun LandingScreenPreview() {
    LandingScreen(
        navigator = MockDestinationsNavigator()
    )
}