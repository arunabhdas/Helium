package app.helium.heliumapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.annotation.Destination
import app.helium.heliumapp.R
import app.helium.heliumapp.destinations.FollowMeScreenDestination
import app.helium.heliumapp.destinations.HelpMeScreenDestination
import app.helium.heliumapp.destinations.RecordingScreenDestination
import app.helium.heliumapp.destinations.ReportIncidentScreenDestination
import app.helium.heliumapp.ui.navigation.MockDestinationsNavigator
import app.helium.heliumapp.ui.screens.composables.HeliumActionItem
import app.helium.heliumapp.ui.theme.ThemeUtils
import app.helium.heliumapp.ui.theme.createGradientEffect
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import timber.log.Timber

@Destination
@Composable
fun HomeScreen(
    navController: NavController,
    navigator: DestinationsNavigator

) {
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
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 2x2 Grid
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                HeliumActionItem(
                    imageId = R.drawable.siren,
                    text = "Incident",
                    contentDescription = stringResource(id = R.string.helium_content_description)
                ) {
                    // TODO: Handle Report Incident Click
                    Timber.d( "Clicked Report Incident")
                    navigator.navigate(
                        ReportIncidentScreenDestination()
                    )
                }
                Spacer(modifier = Modifier.width(30.dp))
                HeliumActionItem(
                    imageId = R.drawable.microphone,
                    text = "Record",
                    contentDescription = stringResource(id = R.string.helium_content_description)
                ) {
                    // TODO: Handle Record Incident Click
                    Timber.d( "Clicked Record")
                    navigator.navigate(
                        RecordingScreenDestination()
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                HeliumActionItem(
                    imageId = R.drawable.nav,
                    text = "Geolocate",
                    contentDescription = stringResource(id = R.string.helium_content_description)
                ) {
                    // TODO: Handle Follow Me Click
                    Timber.d( "Clicked Follow Me")
                    navigator.navigate(
                        FollowMeScreenDestination()
                    )
                }
                Spacer(modifier = Modifier.width(30.dp))
                HeliumActionItem(
                    imageId = R.drawable.exclamation,
                    text = "Help",
                    contentDescription = stringResource(id = R.string.helium_content_description)
                ) {
                    // TODO: Handle Help Click
                    Timber.d("Clicked Help")
                    navigator.navigate(
                        HelpMeScreenDestination()
                    )
                }
            }
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
fun HomeScreenPreview() {
   HomeScreen(
       navController = rememberNavController(),
       navigator = MockDestinationsNavigator()
   )
}