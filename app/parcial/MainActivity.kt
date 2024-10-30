package com.example.parcial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.parcial.DAO.ClienteDao
import com.example.parcial.DAO.ProductoDao
import com.example.parcial.DAO.VentaDao
import com.example.parcial.Database.BasedeDatos
import com.example.parcial.Repository.ClienteRepository
import com.example.parcial.Repository.ProductoRepository
import com.example.parcial.Repository.VentaRepository
import com.example.parcial.Screen.navegacion
import com.example.parcial.ui.theme.Parcial_2Theme

class MainActivity : ComponentActivity() {
    private lateinit var clienteDao: ClienteDao
    private lateinit var clienteRepository: ClienteRepository
    private lateinit var productoDao: ProductoDao
    private lateinit var productoRepository: ProductoRepository
    private lateinit var ventaDao: VentaDao
    private lateinit var ventaRepository: VentaRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = BasedeDatos.getDatabase(applicationContext)
        clienteDao = db.clienteDao()
        clienteRepository = ClienteRepository(clienteDao)
        productoDao = db.productoDao()
        productoRepository = ProductoRepository(productoDao)
        ventaDao = db.ventaDao()
        ventaRepository = VentaRepository(ventaDao)

        enableEdgeToEdge()
        setContent {
            navegacion(clienteRepository,productoRepository,ventaRepository )

        }
    }
}

