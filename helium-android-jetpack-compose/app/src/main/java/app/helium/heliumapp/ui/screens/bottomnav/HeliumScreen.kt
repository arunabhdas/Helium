package app.helium.heliumapp.ui.screens.bottomnav

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.annotation.Destination
import app.helium.heliumapp.R
import app.helium.heliumapp.ui.navigation.MockDestinationsNavigator
import app.helium.heliumapp.ui.screens.composables.HeliumActionItem
import app.helium.heliumapp.ui.theme.ThemeUtils
import app.helium.heliumapp.ui.theme.createGradientEffect
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun HeliumScreen(
    navController: NavController,
    navigator: DestinationsNavigator

) {

    val incidentLocation = LatLng(40.70049391686364,  -74.01470876989087)
    val incidentState = MarkerState(position = incidentLocation)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(incidentLocation, 10f)
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
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp), // Adjust top padding as needed
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }
}



@Preview(showBackground = true, name = "HomeActionItem Preview")
@Composable
fun HomeActionItemPreview() {
    MaterialTheme {
        HeliumActionItem(
            imageId = R.drawable.appicon, // Replace with a valid drawable resource ID
            text = "Sample Text",
            contentDescription = "Sample Description"
        ) {
            // TODO-FIXME onClick
        }
    }
}


@Preview
@Composable
fun HeliumScreenPreview() {
   HeliumScreen(
       navController = rememberNavController(),
       navigator = MockDestinationsNavigator()
   )
}