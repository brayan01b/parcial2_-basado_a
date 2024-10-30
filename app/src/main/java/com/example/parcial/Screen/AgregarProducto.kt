package com.example.parcial.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.parcial.Model.Producto
import com.example.parcial.Repository.ProductoRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAgregarProducto(
    navController: NavController,
    productoRepository: ProductoRepository
) {
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var errorMensaje by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Agregar Producto", color = Color.White)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF4CAF50))
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                        if (it.isBlank()) errorMensaje = "El nombre no puede estar vacío"
                    },
                    label = { Text("Nombre") },
                    isError = nombre.isBlank(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF03DAC5),
                        cursorColor = Color(0xFF6200EA)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = precio,
                    onValueChange = {
                        precio = it
                        if (it.toDoubleOrNull() == null)
                            errorMensaje = "El precio debe ser un número válido"
                    },
                    label = { Text("Precio") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    isError = precio.toDoubleOrNull() == null,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF03DAC5),
                        cursorColor = Color(0xFF6200EA)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = stock,
                    onValueChange = {
                        stock = it
                        if (it.toIntOrNull() == null)
                            errorMensaje = "El stock debe ser un número entero válido"
                    },
                    label = { Text("Stock") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = stock.toIntOrNull() == null,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF03DAC5),
                        cursorColor = Color(0xFF6200EA)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                if (errorMensaje.isNotEmpty()) {
                    Text(
                        text = errorMensaje,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Button(
                    onClick = {
                        if (nombre.isNotBlank() &&
                            precio.toDoubleOrNull() != null &&
                            stock.toIntOrNull() != null) {
                            val producto = Producto(
                                id = 0,
                                nombre = nombre,
                                precio = precio.toDouble(),
                                stock = stock.toInt()
                            )
                            scope.launch {
                                productoRepository.insertarProducto(producto)
                                navController.popBackStack()
                            }
                        } else {
                            errorMensaje = "Todos los campos deben ser válidos"
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Text("Guardar Producto", color = Color.White)
                }
            }
        }
    )
}
