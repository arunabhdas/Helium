package app.helium.heliumapp.data.blescanner

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.helium.heliumapp.ui.theme.TertiaryColor
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HeliumBeaconItem(
    // TODO-FIXME-DEPRECATE navController: NavHostController? = null,
    navigator: DestinationsNavigator,
    dish: HeliumBeacon
) {
    Card(onClick = {
        Log.d("LOGDEBUG", "Click ${dish.id}")

        // TODO-FIXME- navController?.navigate(DishDetails.route + "/${dish.id}")
    }) {
        //TODO: Insert code here
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column() {
                Text(
                    text = dish.name,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(8.dp)
                )
                Text(
                    text = dish.description,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .padding(
                            top = 5.dp,
                            bottom = 5.dp
                        )
                )
                Text(
                    text = "${dish.uuid}",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(8.dp)
                )
            }
            /*
            Image(
                painter = painterResource(
                    id = dish.imageResource),
                contentDescription = "Logo Background",
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
            )
            */
        }
    }
    Divider(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        thickness = 1.dp,
        color = TertiaryColor
    )
}