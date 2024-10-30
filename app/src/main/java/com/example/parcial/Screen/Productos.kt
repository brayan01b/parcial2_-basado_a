package com.example.parcial.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.parcial.Model.Producto
import com.example.parcial.Repository.ProductoRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaListarProductos(navController: NavController, productoRepository: ProductoRepository) {
    var listaProductos by remember { mutableStateOf(listOf<Producto>()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        listaProductos = productoRepository.obtenerTodosLosProductos()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("¡Bienvenido a tu tienda!", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Text(
                    text = "Explora nuestra lista de productos",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(listaProductos) { producto ->
                        ProductoRow(
                            producto = producto,
                            onAgregarStock = { cantidad ->
                                scope.launch {
                                    productoRepository.actualizarProducto(
                                        producto.copy(stock = producto.stock + cantidad)
                                    )
                                    listaProductos = productoRepository.obtenerTodosLosProductos()
                                }
                            },
                            onEliminarProducto = {
                                scope.launch {
                                    productoRepository.eliminarProducto(producto)
                                    listaProductos = productoRepository.obtenerTodosLosProductos()
                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Regresar a Menú Principal", color = Color.White)
                }
            }
        }
    )
}

@Composable
fun ProductoRow(
    producto: Producto,
    onAgregarStock: (Int) -> Unit,
    onEliminarProducto: () -> Unit
) {
    var stockActual by remember { mutableStateOf(producto.stock) }
    var mostrarDialogo by remember { mutableStateOf(false) }

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Confirmar Eliminación") },
            text = { Text("¿Estás seguro de que deseas eliminar ${producto.nombre}? Esta acción no se puede deshacer.") },
            confirmButton = {
                TextButton(onClick = {
                    onEliminarProducto()
                    mostrarDialogo = false
                }) {
                    Text("Sí, Eliminar", color = MaterialTheme.colorScheme.primary)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogo = false }) {
                    Text("Cancelar", color = MaterialTheme.colorScheme.error)
                }
            }
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = producto.nombre, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
                Text(text = "Stock disponible: $stockActual", style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        if (stockActual > 0) {
                            stockActual -= 1
                            onAgregarStock(-1)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Restar stock",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        stockActual += 1
                        onAgregarStock(1)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar stock",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                IconButton(onClick = { mostrarDialogo = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar Producto",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
