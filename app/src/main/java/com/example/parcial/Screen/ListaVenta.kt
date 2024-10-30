package com.example.parcial.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.parcial.Model.Venta
import com.example.parcial.Repository.VentaRepository
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaListarVentas(
    navController: NavController,
    ventaRepository: VentaRepository
) {
    var listaVentas by remember { mutableStateOf(listOf<Venta>()) }

    LaunchedEffect(Unit) {
        listaVentas = ventaRepository.obtenerTodasLasVentas()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Lista de Ventas", style = MaterialTheme.typography.titleLarge)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(listaVentas) { venta ->
                        VentaRow(venta = venta)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Regresar", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    )
}

fun convertirFecha(fechaMillis: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(Date(fechaMillis))
}

@Composable
fun VentaRow(venta: Venta) {
    val fechaFormateada = convertirFecha(venta.fecha)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "ID Venta: ${venta.id}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Cliente ID: ${venta.clienteId}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Producto ID: ${venta.productoId}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Cantidad: ${venta.cantidad}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Fecha: $fechaFormateada",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
