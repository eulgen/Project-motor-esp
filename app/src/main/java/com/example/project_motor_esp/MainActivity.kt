package com.example.project_motor_esp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import androidx.navigation.NavHostController
import com.example.project_motor_esp.ui.theme.ProjectmotorespTheme
import java.io.PrintWriter
import java.net.Socket

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjectmotorespTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainProject(modifier = Modifier)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainProject(modifier: Modifier = Modifier){
    var checked by remember { mutableStateOf(false) }
    var navigationSelectedItem by remember {
        mutableStateOf(0)
    }
    var state = "Disconnected"
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                            text = "Home",
                            color = colorResource(R.color.white_600),
                            fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    Text(
                        text = state,
                        color = colorResource(R.color.white_600)
                    )
                    Switch(
                        checked = checked,
                        onCheckedChange = { checked = it },
                        modifier = Modifier.padding(10.dp),
                        colors = SwitchColors(
                                checkedThumbColor = colorResource(R.color.white_600),
                                checkedTrackColor = colorResource(R.color.white_500),
                                checkedBorderColor = colorResource(R.color.white_500),
                                checkedIconColor = colorResource(R.color.white_600),
                                uncheckedThumbColor = colorResource(R.color.white_600),
                                uncheckedTrackColor = colorResource(R.color.white_300),
                                uncheckedBorderColor = colorResource(R.color.white_300),
                                uncheckedIconColor = colorResource(R.color.white_600),
                                disabledCheckedThumbColor = colorResource(R.color.white_200),
                                disabledCheckedTrackColor = colorResource(R.color.white_200),
                                disabledCheckedBorderColor = colorResource(R.color.white_200),
                                disabledCheckedIconColor = colorResource(R.color.white_200),
                                disabledUncheckedThumbColor = colorResource(R.color.white_200),
                                disabledUncheckedTrackColor = colorResource(R.color.white_200),
                                disabledUncheckedBorderColor = colorResource(R.color.white_200),
                                disabledUncheckedIconColor = colorResource(R.color.white_600)
                        )
                    )
//                    state = if(checked){
//                        "Disconnected"
//                    }else{
//                        "Connected"
//                    }
                    if (checked){
                        state = "Disconnect"
                    }else{
                        state = "Connected"
//                        print("Ip : "+GlobalVariables.ipadress+ " Port : "+GlobalVariables.port+"\n")
                    }
                },
                colors = TopAppBarColors(
                        containerColor = colorResource(R.color.white_200),
                        scrolledContainerColor = colorResource(R.color.white_600),
                        navigationIconContentColor = colorResource(R.color.white_600),
                        titleContentColor = colorResource(R.color.white_600),
                        actionIconContentColor = colorResource(R.color.white_600),
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { paddingValues ->
        Navigation(navController,paddingValues)
    }
}

fun connectToServer(ip: String, port: Int): String {
    return try {
        val socket = Socket(ip, port)
        val output = PrintWriter(socket.getOutputStream(), true)

        // Vous pouvez envoyer des données au serveur ici si nécessaire
        output.println("Hello from client!")

        socket.close()
        "Connected to $ip:$port"
    } catch (e: Exception) {
        "Connection failed: ${e.message}"
    }
}

@Preview(showBackground = true, device = Devices.NEXUS_5)
@Composable
fun MainProjectView() {
    ProjectmotorespTheme {
        MainProject()
    }
}

@Composable
fun Navigation(navController: NavHostController,paddingValues: PaddingValues){
    NavHost(
            navController = navController,
            startDestination = NavigationRoute.Home.route,
            modifier = Modifier.padding(paddingValues = paddingValues)) {
        composable(NavigationRoute.Home.route) {
            HomeScreen(
                    navController
            )
        }
        composable(NavigationRoute.Settings.route) {
            SettingsScreen(
                    navController
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController){
    var navigationSelectedItem by remember {
        mutableStateOf(0)
    }
    NavigationBar(
        containerColor = colorResource(R.color.white_200)
    ) {
        BottomNavigationItem().bottomNavigationItems().forEachIndexed {index,navigationItem ->
            NavigationBarItem(
                    selected = index == navigationSelectedItem,
                    label = {
                        Text(navigationItem.label)
                    },
                    icon = {
                        Icon(
                                navigationItem.icon,
                                contentDescription = navigationItem.label
                        )
                    },
                    colors = NavigationBarItemColors(
                            selectedIndicatorColor = colorResource(R.color.white_300),
                            selectedIconColor = colorResource(R.color.black_200),
                            selectedTextColor = colorResource(R.color.black_200),
                            unselectedIconColor = colorResource(R.color.black_200),
                            unselectedTextColor = colorResource(R.color.black_200),
                            disabledIconColor = colorResource(R.color.black_200),
                            disabledTextColor = colorResource(R.color.black_200)
                    ),
                    onClick = {
                        navigationSelectedItem = index
                        navController.navigate(navigationItem.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
            )
        }
    }
}

//@Composable
//@Preview(showBackground = true)
//fun BottomNavigationBarView(){
//    val navController = rememberNavController()
//    BottomNavigationBar(navController = navController)
//}