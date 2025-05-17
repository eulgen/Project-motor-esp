package com.example.project_motor_esp

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationRoute(var route :String)
{
    data object Home : NavigationRoute("home")
    data object Settings : NavigationRoute("settings")
}

//initializing the data class with default parameters
data class BottomNavigationItem(
        val label : String = "",
        val icon : ImageVector = Icons.Filled.Home,
        val route : String = ""
) {

    //function to get the list of bottomNavigationItems
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
                BottomNavigationItem(
                        label = "Home",
                        icon = Icons.Filled.Home,
                        route = NavigationRoute.Home.route
                ),
                BottomNavigationItem(
                        label = "Settings",
                        icon = Icons.Filled.Settings,
                        route = NavigationRoute.Settings.route
                )
        )
    }
}