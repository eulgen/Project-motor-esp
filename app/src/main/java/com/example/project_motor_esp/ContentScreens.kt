package com.example.project_motor_esp

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

//---------------------Home Screen-------------------------

@Composable
fun HomeScreen(navController : NavHostController){
    Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.white_100))
                .wrapContentSize(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
                text = "Home page",
                color = colorResource(R.color.white_600),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(30.dp))
        BodyHome(modifier = Modifier)
    }
}

@Composable
fun BodyHome(modifier: Modifier = Modifier) {
    Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
                modifier= Modifier.size(200.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonColors(
                        containerColor = colorResource(R.color.white_600),
                        contentColor = colorResource(R.color.white_100),
                        disabledContentColor = colorResource(R.color.white_100),
                        disabledContainerColor = colorResource(R.color.white_500)
                ),
                onClick = { /*TODO*/ },
        ) {
            Icon(
                    Icons.Filled.PlayArrow,
                    contentDescription = "Arrow icon",
                    tint = colorResource(R.color.white_100),
                    modifier = Modifier.size(80.dp)
            )
        }
    }
}

@Composable
fun roundedButton() {
    Button(
            onClick = { },
//        shape = RoundedCornerShape(10.dp),
            shape = CutCornerShape(20),
            modifier = Modifier.padding(20.dp)
    ) {
        Text("Rounded Button")
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(){
    val navController = rememberNavController()
    HomeScreen(navController)
}

//---------------------Settings Screen-------------------------

@Composable
fun SettingsScreen(navController : NavHostController){

    Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.white_100))
                .wrapContentSize(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
                text = "Settings",
                color = colorResource(R.color.white_600),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(15.dp))
        val ipadress = ValidateIp()
        Spacer(modifier = Modifier.height(15.dp))
        val port = ValidatePort()
        Spacer(modifier = Modifier.height(15.dp))
        val time = ValidTIme()
        Spacer(modifier = Modifier.height(15.dp))
        val state = SettingsButton(ipadress,port,time)
        GlobalVariables.ipadress = ipadress
        GlobalVariables.port = port
        GlobalVariables.time = time
    }
}

@Composable
@Preview(showBackground = true)
fun SettingsScreenPreview(){
    val navController = rememberNavController()
    SettingsScreen(navController)
}

@Composable
fun TimeTextField(time :String,onTimeChange: (String) -> Unit){
    OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value =  time ,
            onValueChange = {onTimeChange(it)},
            label = { Text("Time in minute", fontSize = 15.sp) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

fun isValidTime(time:String): Boolean{
    val portRegex = "[0-9]+".toRegex()
    return time.matches(portRegex)
}

@Composable
fun ValidTIme():String{
    var time by remember { mutableStateOf("") }
    Column (modifier = Modifier.padding(16.dp)) {
        TimeTextField(time, onTimeChange = {time=it})
        if(time.isNotEmpty()){
            if (isValidTime(time)){
                Text("time is valid", color = Color.Blue, fontSize = 10.sp)
                return time
            }else{
                Text("time is not valid", color = Color.Red, fontSize = 10.sp)
            }
        }
    }
    return ""
}

@Composable
fun PortTextField(port: String, onPortChange: (String) -> Unit){
    OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = port,
            onValueChange = { onPortChange(it) },
            label = { Text("Port", fontSize = 15.sp) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

fun isValidPort(port:String): Boolean{
    val portRegex = "[0-9]+".toRegex()
    return port.matches(portRegex)
}

@Composable
fun ValidatePort() :String{
    var port by remember { mutableStateOf("") }
    Column (modifier = Modifier.padding(16.dp)) {
        PortTextField(port, onPortChange = {port=it})
        if(port.isNotEmpty()){
            if (isValidPort(port)){
                Text("port is valid", color = Color.Blue, fontSize = 10.sp)
                return port
            }else{
                Text("port is not valid", color = Color.Red, fontSize = 10.sp)
            }
        }
    }
    return ""
}

@Composable
fun IpaddressTextField(ipaddress: String, onIpChange: (String) -> Unit) {
    OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = ipaddress,
            onValueChange = { onIpChange(it) },
            label = { Text("Ip Address", fontSize = 15.sp) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

fun isValidIp(ipaddress: String): Boolean {
    val ipRegex = ("^(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])." +
            "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])." +
            "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])." +
            "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])$").toRegex()
    return ipaddress.matches(ipRegex)
}

@Composable
fun ValidateIp(): String {
    var ipaddress by remember { mutableStateOf("") }
    Column (modifier = Modifier.padding(16.dp)) {
        IpaddressTextField(ipaddress = ipaddress, onIpChange = { ipaddress = it })

        if (ipaddress.isNotEmpty()) {
            if (isValidIp(ipaddress)) {
                Text(text = "ip address is valid", color = Color.Blue, fontSize = 10.sp)
                return ipaddress
            } else {
                Text(text = "ip address is not valid", color = Color.Red, fontSize = 10.sp)
            }
        }
    }
    return ""
}

//@Composable
//@Preview(showBackground = true)
//fun ValidateIpPreview(){
//    var ipvalue = ""
//    ipvalue = ValidateIp()
//}

@Composable
fun SettingsButton(ipaddr: String, port: String, time: String):Boolean{
    var state by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonColors(
                    containerColor = colorResource(R.color.white_600),
                    contentColor = colorResource(R.color.white_200),
                    disabledContentColor = colorResource(R.color.white_200),
                    disabledContainerColor = colorResource(R.color.white_550)
            ),
            shape = CutCornerShape(50),
            enabled = state,
            onClick = {
                    if(ipaddr.isEmpty()&&port.isEmpty()&&time.isEmpty()){
                        state = false
                        Toast.makeText(context,"Please fill in the fields",Toast.LENGTH_LONG).show()
                    }else{
                        state = true
                        Toast.makeText(context,"Values correctly save",Toast.LENGTH_LONG).show()
                    }
            }
    ) {
        Text(
                text = "Save",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
        )
    }
    return state
}

//@Composable
//@Preview(showBackground = true)
//fun SettingsButtonPreview(){
//    SettingsButton()
//}