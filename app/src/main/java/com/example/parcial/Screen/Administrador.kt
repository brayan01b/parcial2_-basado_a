package com.example.parcial.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAdministrador(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Administrador", color = Color.White, fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF6200EA)) // Color de fondo
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceEvenly, // Espaciado uniforme
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Botón para agregar un producto
            Button(
                onClick = { navController.navigate("agregarProducto") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp), // Margen horizontal para los botones
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03DAC5)) // Color botón
            ) {
                Text("Que producto deseas Agregar", color = Color.White)
            }


            Button(
                onClick = { navController.navigate("Clientes") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03DAC5))
            ) {
                Text("Lista de Clientes", color = Color.White)
            }

            Button(
                onClick = { navController.navigate("Productos") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03DAC5))
            ) {
                Text(" Lista de Productos", color = Color.White)
            }

            Button(
                onClick = { navController.navigate("historial") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03DAC5))
            ) {
                Text("Lista de Ventas", color = Color.White)
            }
        }
    }
}
