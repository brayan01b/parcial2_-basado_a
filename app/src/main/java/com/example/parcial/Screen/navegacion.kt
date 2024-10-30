package com.example.parcial.Screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.parcial.Repository.ClienteRepository
import com.example.parcial.Repository.ProductoRepository
import com.example.parcial.Repository.VentaRepository

@Composable
fun navegacion(
    clienteRepository: ClienteRepository,
    productoRepository: ProductoRepository,
    ventaRepository: VentaRepository
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "principal") {
        composable("principal") {
            PantallaPrincipal(navController)
        }
        composable("Administrador") {
            PantallaAdministrador(navController)
        }
        composable("agregarProducto") {
            PantallaAgregarProducto(navController, productoRepository)
        }
        composable("Clientes") {
            PantallaListarClientes(navController, clienteRepository)
        }
        composable("Productos") {
            PantallaListarProductos(navController = navController, productoRepository = productoRepository)
        }
        composable("agregarCliente") {
            PantallaAgregarCliente(navController, clienteRepository)
        }
        composable("Venta/{clienteId}/{cantidad}") { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getString("clienteId")?.toInt() ?: 0
            val cantidad = backStackEntry.arguments?.getString("cantidad")?.toInt() ?: 0
            PantallaVenta(
                navController = navController,
                ventaRepo = ventaRepository,
                productoRepo = productoRepository,
                idCliente = clienteId,
                cantidadProducto = cantidad
            )
        }
        composable("compra/{clienteId}") { backStackEntry ->
            val clienteId = backStackEntry.arguments?.getString("clienteId")?.toInt() ?: 0
            PantallaProductos(navController = navController, productoRepository = productoRepository, clienteId = clienteId)
        }
        composable("Ingresar") {
            PantallaIngresarClientes(navController, clienteRepository)
        }
        composable("historial") {
            PantallaListarVentas(navController = navController, ventaRepository = ventaRepository)
        }
    }
}
