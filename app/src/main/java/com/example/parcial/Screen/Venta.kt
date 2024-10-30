package com.example.parcial.Screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.parcial.Model.Producto
import com.example.parcial.Model.Venta
import com.example.parcial.Repository.ProductoRepository
import com.example.parcial.Repository.VentaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.round

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun PantallaVenta(
    navController: NavController,
    ventaRepo: VentaRepository,
    productoRepo: ProductoRepository,
    idCliente: Int,
    cantidadProducto: Int
) {
    var totalPrecio by remember { mutableStateOf(0.0) }
    val coroutineScope = rememberCoroutineScope()
    var carritoProductos by remember { mutableStateOf(listOf<Producto>()) }

    LaunchedEffect(Unit) {
        carritoProductos = withContext(Dispatchers.IO) {
            productoRepo.obtenerProductosEnCarrito()
        }
    }

    LaunchedEffect(carritoProductos) {
        totalPrecio = carritoProductos.sumOf { it.precio * cantidadProducto }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Carrito de Compras", style = MaterialTheme.typography.headlineLarge)
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Productos en tu carrito",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(carritoProductos) { producto ->
                        ItemProducto(producto, cantidadProducto)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Total a Pagar: \$${round(totalPrecio * 100) / 100}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.align(Alignment.End)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.weight(1f).padding(4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Volver", color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                carritoProductos.forEach { producto ->
                                    val nuevaVenta = Venta(
                                        id = 0,
                                        productoId = producto.id,
                                        clienteId = idCliente,
                                        cantidad = cantidadProducto,
                                        fecha = System.currentTimeMillis()
                                    )
                                    ventaRepo.insertarVenta(nuevaVenta)
                                    productoRepo.actualizarProducto(
                                        producto.copy(stock = producto.stock - cantidadProducto)
                                    )
                                }
                                productoRepo.vaciarCarrito()
                                navController.navigate("principal")
                            }
                        },
                        modifier = Modifier.weight(1f).padding(4.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text("Confirmar Pago", color = Color.White)
                    }
                }
            }
        }
    )
}

@Composable
fun ItemProducto(producto: Producto, cantidad: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
                )
                Text(
                    text = "Precio: \$${producto.precio}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Cantidad: $cantidad",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = "Subtotal: \$${round(producto.precio * cantidad * 100) / 100}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}
