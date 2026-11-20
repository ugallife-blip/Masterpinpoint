package com.masterpinpoint.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Navy = Color(0xFF101827)
private val Panel = Color(0xFF1B2638)
private val Muted = Color(0xFF9AA8BC)
private val Green = Color(0xFF35C98A)
private val Red = Color(0xFFFF6878)
private val Gold = Color(0xFFFFC857)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme(colorScheme = darkColorScheme(
            background = Navy, surface = Panel, primary = Gold,
            onBackground = Color.White, onSurface = Color.White
        )) { Dashboard() } }
    }
}

@Composable
fun Dashboard() {
    var capital by remember { mutableStateOf("1000") }
    var risk by remember { mutableStateOf("1") }
    var entry by remember { mutableStateOf("0") }
    var stop by remember { mutableStateOf("0") }
    val c = capital.toDoubleOrNull() ?: 0.0
    val r = risk.toDoubleOrNull() ?: 0.0
    val e = entry.toDoubleOrNull() ?: 0.0
    val s = stop.toDoubleOrNull() ?: 0.0
    val riskCash = c * r / 100.0
    val distance = kotlin.math.abs(e - s)
    val riskPercent = if (c > 0) riskCash / c * 100 else 0.0

    Column(
        Modifier.fillMaxSize().background(Navy).verticalScroll(rememberScrollState()).padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text("MASTER PIN POINT", color = Gold, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("GOLD • MARKET ANALYSIS", color = Muted, fontSize = 11.sp)
            }
            Surface(color = Panel, shape = RoundedCornerShape(50)) {
                Text("AUTO TRADING: OFF", Modifier.padding(horizontal = 12.dp, vertical = 8.dp), color = Green, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
        }

        Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(18.dp)) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("XAU / USD", color = Muted, fontSize = 12.sp)
                Text("LIVE DATA NOT CONNECTED", color = Gold, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("Price feed will appear here after a verified market-data source is connected.", color = Muted, fontSize = 12.sp)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("M1", "M5", "M15", "H1", "H4").forEach {
                        Surface(color = if (it == "M15") Gold.copy(alpha = .18f) else Navy, shape = RoundedCornerShape(8.dp)) {
                            Text(it, Modifier.padding(horizontal = 12.dp, vertical = 8.dp), color = if (it == "M15") Gold else Muted, fontSize = 12.sp)
                        }
                    }
                }
                Box(Modifier.fillMaxWidth().height(150.dp).background(Navy, RoundedCornerShape(12.dp)), contentAlignment = Alignment.Center) {
                    Text("Candlestick chart area", color = Muted, fontSize = 13.sp)
                }
            }
        }

        Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(18.dp)) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("SIGNAL PANEL", color = Gold, fontWeight = FontWeight.Bold)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Signal("BUY", Green); Signal("SELL", Red); Signal("WAIT", Gold)
                }
                Text("No trade signal until live market data and signal rules are validated.", color = Muted, fontSize = 12.sp)
                Divider(color = Navy)
                Text("Entry • Stop Loss • Take Profit", color = Color.White, fontWeight = FontWeight.SemiBold)
                Text("These fields will be calculated from confirmed market structure, not guessed.", color = Muted, fontSize = 12.sp)
            }
        }

        Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(18.dp)) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("FLEXIBLE RISK CALCULATOR", color = Gold, fontWeight = FontWeight.Bold)
                NumberField("Account balance (any amount)", capital) { capital = it }
                NumberField("Risk per trade (%)", risk) { risk = it }
                NumberField("Entry price", entry) { entry = it }
                NumberField("Stop-loss price", stop) { stop = it }
                Divider(color = Navy)
                ResultRow("Risk amount", "%.2f".format(riskCash))
                ResultRow("Risk percentage", "%.2f%%".format(riskPercent))
                ResultRow("Entry-to-stop distance", "%.2f".format(distance))
                Text("Position size needs the instrument contract specification and account currency; it is intentionally not guessed in this first build.", color = Muted, fontSize = 11.sp)
            }
        }
        Text("Build 0.1.0 • Analysis only • No broker login • No order execution", color = Muted, fontSize = 10.sp)
    }
}

@Composable
fun Signal(label: String, color: Color) {
    Surface(color = color.copy(alpha = .14f), shape = RoundedCornerShape(10.dp)) {
        Text(label, Modifier.padding(horizontal = 18.dp, vertical = 10.dp), color = color, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun NumberField(label: String, value: String, onChange: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(label, color = Muted, fontSize = 11.sp)
        OutlinedTextField(
            value = value, onValueChange = { onChange(it.filter { ch -> ch.isDigit() || ch == '.' }) },
            modifier = Modifier.fillMaxWidth(), singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Gold, unfocusedBorderColor = Muted,
                focusedTextColor = Color.White, unfocusedTextColor = Color.White
            )
        )
    }
}

@Composable
fun ResultRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = Muted, fontSize = 12.sp)
        Text(value, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
    }
}
