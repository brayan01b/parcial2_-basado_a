package com.example.parcial.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.parcial.Model.Producto
import com.example.parcial.Repository.ProductoRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaProductos(
    navController: NavController,
    productoRepository: ProductoRepository,
    clienteId: Int
) {
    val productos = remember { mutableStateListOf<Producto>() }
    var cantidad by remember { mutableStateOf(1) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        productos.addAll(productoRepository.obtenerTodosLosProductos())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Productos Disponibles", style = MaterialTheme.typography.headlineMedium)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("Venta/$clienteId/$cantidad") },
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Ir al Carrito", tint = Color.White)
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(productos) { producto ->
                        ProductoCard(
                            producto = producto,
                            cantidad = cantidad,
                            onCantidadChange = { nuevaCantidad -> cantidad = nuevaCantidad },
                            onAgregarCarrito = {
                                scope.launch { productoRepository.agregarAlCarrito(producto) }
                            }
                        )
                    }
                }
                // Separador opcional
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    )
}

@Composable
fun ProductoCard(
    producto: Producto,
    cantidad: Int,
    onCantidadChange: (Int) -> Unit,
    onAgregarCarrito: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Precio: $${producto.precio}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Stock: ${producto.stock}",
                style = MaterialTheme.typography.bodyMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { if (cantidad > 1) onCantidadChange(cantidad - 1) },
                    enabled = cantidad > 1
                ) {
                    Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = "Disminuir cantidad")
                }
                Text(
                    text = cantidad.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                IconButton(
                    onClick = { if (cantidad < producto.stock) onCantidadChange(cantidad + 1) },
                    enabled = cantidad < producto.stock
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Aumentar cantidad")
                }
            }

            // Botón para agregar al carrito
            Button(
                onClick = onAgregarCarrito,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Agregar al Carrito", style = MaterialTheme.typography.labelLarge, color = Color.White)
            }
        }
    }
}
