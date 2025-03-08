package app.helium.heliumapp.ui.screens.bottomnav

import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.helium.heliumapp.destinations.NotifScreenDestination
import app.helium.heliumapp.ui.navigation.CustomBottomNavAppBar
import com.exyte.animatednavbar.AnimatedNavigationBar
import com.exyte.animatednavbar.animation.balltrajectory.Parabolic
import com.exyte.animatednavbar.animation.indendshape.Height
import com.exyte.animatednavbar.animation.indendshape.shapeCornerRadius
import com.exyte.animatednavbar.utils.noRippleClickable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import timber.log.Timber

@Destination
@Composable
fun MainScreenBottomNavigation(
    navController: NavController,
    navigator: DestinationsNavigator
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    var navigationBarItems = remember { NavigationBarItems.values() }
    var selectedIndex by remember { mutableStateOf(2) }
    Scaffold(
        modifier = Modifier.padding(all = 0.dp),
        topBar = {
            CustomBottomNavAppBar(
                onLeftNavigationBarItemClick = {
                    scope.launch {
                        Timber.d("Helium - MainScreenBottomNavigation - Left")
                        navigator.navigate(NotifScreenDestination())
                    }
                },
                onRightNavigationBarItemClick = {
                    scope.launch {
                        Timber.d("Helium - MainScreenBottomNavigation - Right")
                    }
                }
            )
        },
        bottomBar = {
            AnimatedNavigationBar(
                selectedIndex = selectedIndex,
                modifier = Modifier
                    .height(128.dp)
                    .navigationBarsPadding(),
                cornerRadius = shapeCornerRadius(cornerRadius = 4.dp),
                ballAnimation = Parabolic(tween(300)),
                indentAnimation = Height(tween(300)),
                barColor = MaterialTheme.colorScheme.onPrimary,
                ballColor = MaterialTheme.colorScheme.primary
            ) {
                navigationBarItems.forEach { item ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .noRippleClickable { selectedIndex = item.ordinal },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(26.dp)
                                .padding(bottom = 5.dp),
                            painter = painterResource(id = item.icon),
                            contentDescription = "Bottom Bar Icon",
                            tint = if (selectedIndex == item.ordinal) MaterialTheme.colorScheme.onBackground
                            else MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    ) { padding ->
        // Main content

        // Switching content based on selectedIndex
        when (selectedIndex) {
            0 -> ReportScreen(
                navController = navController,
                navigator = navigator
            )
            1 -> RecordScreen(
                navController = navController,
                navigator = navigator
            )
            2 -> HeliumScreen(
                navController = navController,
                navigator = navigator
            )
            3 -> NotifScreen(
                navController = navController,
                navigator = navigator
            )
            4 -> MoreScreen(
                navController = navController,
                navigator = navigator
            )
            else -> Text("Screen not found")
        }

    }
}


fun Modifier.noRippleClickable(
    onClick: () -> Unit
) = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}



