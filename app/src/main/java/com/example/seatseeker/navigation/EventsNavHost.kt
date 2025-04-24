import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.seatseeker.screens.EventsScreen
import com.example.seatseeker.screens.EventsWebView
import com.example.seatseeker.screens.SingleEventScreen
import com.example.seatseeker.navigation.Event
import com.example.seatseeker.navigation.EventWeb
import com.example.seatseeker.navigation.Home


@Composable
fun EventsNavHost(
    navController: NavHostController,

    ) {
    NavHost(navController = navController, startDestination = Home.route) {
        composable(route = Home.route) {
            EventsScreen(onEventSelected = {
                navController.navigateSingleTopTo("${Event.route}/${it}")
            })
        }

        composable(
            route = Event.routeWithArgs,
            arguments = Event.arguments
        ) { navBackStackEntry ->
            val event = navBackStackEntry.arguments?.getString(Event.eventID)
            if (event != null) {
                SingleEventScreen {
                    navController.navigateSingleTopTo(
                        "${EventWeb.route}/${it}"
                    )
                }
            }
        }

        composable(
            route = EventWeb.routeWithArgs,
            arguments = EventWeb.arguments
        ) { navBackStackEntry ->
            val venueUrl = navBackStackEntry.arguments?.getString(EventWeb.venueURL)
            if (venueUrl != null) {
                EventsWebView(eventURL = venueUrl, modifier = Modifier)
            }
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }