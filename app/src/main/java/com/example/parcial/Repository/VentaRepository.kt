package com.example.parcial.Repository

import com.example.parcial.DAO.VentaDao
import com.example.parcial.Model.Producto
import com.example.parcial.Model.Venta

class VentaRepository(private val ventaDao: VentaDao) {


    suspend fun insertarVenta(venta: Venta) {
        ventaDao.insertarVenta(venta)
    }

    suspend fun actualizarVenta(venta: Venta) {
        ventaDao.actualizarVenta(venta)
    }

    suspend fun eliminarVenta(venta: Venta) {
        ventaDao.eliminarVenta(venta)
    }

    suspend fun obtenerVentaPorId(id: Int): Venta? {
        return ventaDao.obtenerVentaPorId(id)
    }

    suspend fun obtenerVentasPorCliente(clienteId: Int): List<Venta> {
        return ventaDao.obtenerVentasPorCliente(clienteId)
    }

    suspend fun obtenerVentasPorProducto(productoId: Int): List<Venta> {
        return ventaDao.obtenerVentasPorProducto(productoId)
    }

    suspend fun obtenerTodasLasVentas(): List<Venta> {
        return ventaDao.obtenerTodasLasVentas()
    }
}
