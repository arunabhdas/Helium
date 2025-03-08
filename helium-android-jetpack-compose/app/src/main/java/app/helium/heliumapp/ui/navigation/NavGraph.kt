package app.helium.heliumapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import app.helium.heliumapp.ui.screens.SettingsScreen
import app.helium.heliumapp.ui.screens.HomeScreen
import app.helium.heliumapp.ui.screens.NotificationScreen
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun SetupDrawerNavGraph(
    navController: NavHostController,
    navigator: DestinationsNavigator
) {
    NavHost(
        navController = navController,
        startDestination = ScreenDrawer.HomeScreen.route
        // TODO-FIXME-CLEANUP startDestination = Screen.MainScreen.route
    ) {

        composable(
            route = ScreenDrawer.HomeScreen.route
        ) {
            HomeScreen(
                navController = navController,
                navigator = navigator
            )
        }

        composable(
            route = ScreenDrawer.SettingsScreen.route
        ) {
            SettingsScreen(
                navController = navController
            )
        }

        composable(
            route = ScreenDrawer.NotificationScreen.route
        ) {
            NotificationScreen(
                navController = navController
            )
        }

    }
}
/* TODO-FIXME-DEPRECATE
@Composable
fun SetupRootNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.LandingScreen.route
        // TODO-FIXME-CLEANUP startDestination = Screen.MainScreen.route
    ) {

        /*
        composable(
            route = Screen.LandingScreen.route
        ) {
            LandingScreen(
                navController = navController
            )
        }
        */

        composable(
            route = Screen.LoginScreen.route
        ) {
            LoginScreen(
                navController = navController
            )
        }

        composable(
            route = Screen.RegistrationScreen.route
        ) {
            RegistrationScreen(
                navController = navController
            )
        }

        composable(
            route = Screen.MainScreen.route
        ) {
            MainScreen(
                navController = navController
            )
        }
    }
}
*/