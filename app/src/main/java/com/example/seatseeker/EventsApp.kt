package com.example.seatseeker

import EventsNavHost
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.seatseeker.navigation.bottomNavOptions
import com.example.seatseeker.ui.theme.SeatSeekerTheme
import com.example.seatseeker.navigation.Home


@Composable
fun EventsApp() {
    SeatSeekerTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        bottomNavOptions.find { it.route == currentDestination?.route } ?: Home

        Scaffold(
            content = { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Text(text = "SeatSeeker")

                    }
                    EventsNavHost(
                        navController = navController
                    )
                }

            }
        )
    }
}
