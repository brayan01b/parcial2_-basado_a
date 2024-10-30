package com.example.parcial.Repository

import com.example.parcial.DAO.ProductoDao
import com.example.parcial.Model.Producto

class ProductoRepository(private val productoDao: ProductoDao) {

    // Lista en memoria para simular productos en el carrito
    private val productosEnCarrito = mutableListOf<Producto>()

    suspend fun insertarProducto(producto: Producto) {
        productoDao.insertarProducto(producto)
    }

    suspend fun actualizarProducto(producto: Producto) {
        productoDao.actualizarProducto(producto)
    }

    suspend fun eliminarProducto(producto: Producto) {
        productoDao.eliminarProducto(producto)
    }

    suspend fun obtenerProductoPorId(id: Int): Producto? {
        return productoDao.obtenerProductoPorId(id)
    }

    suspend fun obtenerTodosLosProductos(): List<Producto> {
        return productoDao.obtenerTodosLosProductos()
    }

    // Función para agregar productos al carrito
    fun agregarAlCarrito(producto: Producto) {

        productosEnCarrito.add(producto)
    }


    // Función para eliminar productos del carrito
    fun eliminarDelCarrito(producto: Producto) {
        productosEnCarrito.remove(producto)
    }

    // Función para obtener los productos en el carrito
    fun obtenerProductosEnCarrito(): List<Producto> {
        return productosEnCarrito
    }

    // Función para vaciar el carrito (opcional)
    fun vaciarCarrito() {
        productosEnCarrito.clear()
    }
}

