package com.example.seatseeker.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface EventsDestination {
    val icon: ImageVector?
    val route: String
    val title: String
}

object Home : EventsDestination {
    override val icon = Icons.Filled.Home
    override val route = "home"
    override val title = "Home"
}

object Event : EventsDestination {
    override val icon: Nothing? = null
    override val title = "Event"
    override val route = "event"
    const val eventID = "event_id"
    val routeWithArgs = "${route}/{${eventID}}"
    val arguments = listOf(navArgument(eventID) { type = NavType.StringType })
}

object EventWeb : EventsDestination {
    override val icon: Nothing? = null
    override val route = "web"
    override val title = "WebView"
    const val venueURL = "venue_url"
    val routeWithArgs = "${route}/{${venueURL}}"
    val arguments = listOf(navArgument(venueURL){type = NavType.StringType})
}

val bottomNavOptions = listOf(Home, Event)