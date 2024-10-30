package com.example.parcial.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.parcial.Model.Cliente
import com.example.parcial.Repository.ClienteRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaListarClientes(navController: NavController, clienteRepository: ClienteRepository) {
    var listaClientes by remember { mutableStateOf(listOf<Cliente>()) }
    var mostrarDialogo by remember { mutableStateOf(false) }
    var clienteSeleccionado by remember { mutableStateOf<Cliente?>(null) }

    val scope = rememberCoroutineScope()

    // Cargar los clientes desde la base de datos
    LaunchedEffect(Unit) {
        listaClientes = clienteRepository.obtenerTodosLosClientes()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Clientes", style = MaterialTheme.typography.headlineMedium) }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(listaClientes) { cliente ->
                        ClienteRow(
                            cliente = cliente,
                            onEliminarCliente = {
                                clienteSeleccionado = cliente
                                mostrarDialogo = true
                            }
                        )
                    }
                }

                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Regresar")
                }
            }
        }
    )

    // Diálogo de confirmación
    if (mostrarDialogo && clienteSeleccionado != null) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Eliminar Cliente") },
            text = {
                Text("¿Estás seguro de que deseas eliminar a ${clienteSeleccionado?.nombre}?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        scope.launch {
                            clienteSeleccionado?.let { clienteRepository.eliminarCliente(it) }
                            listaClientes = clienteRepository.obtenerTodosLosClientes()
                        }
                        mostrarDialogo = false
                    }
                ) {
                    Text("Sí", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogo = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun ClienteRow(cliente: Cliente, onEliminarCliente: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
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
                Text(text = cliente.nombre, style = MaterialTheme.typography.titleMedium)
                Text(text = "Email: ${cliente.correo}", style = MaterialTheme.typography.bodyMedium)
            }

            IconButton(onClick = onEliminarCliente) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar Cliente",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

