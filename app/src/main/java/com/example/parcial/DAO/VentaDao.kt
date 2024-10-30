package com.example.parcial.DAO

import androidx.room.*
import com.example.parcial.Model.Venta

@Dao
interface VentaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarVenta(venta: Venta)

    @Update
    suspend fun actualizarVenta(venta: Venta)

    @Delete
    suspend fun eliminarVenta(venta: Venta)

    @Query("SELECT * FROM ventas WHERE id = :id")
    suspend fun obtenerVentaPorId(id: Int): Venta?

    @Query("""
        SELECT * FROM ventas 
        WHERE clienteId = :clienteId
    """)
    suspend fun obtenerVentasPorCliente(clienteId: Int): List<Venta>

    @Query("""
        SELECT * FROM ventas 
        WHERE productoId = :productoId
    """)
    suspend fun obtenerVentasPorProducto(productoId: Int): List<Venta>

    @Query("SELECT * FROM ventas")
    suspend fun obtenerTodasLasVentas(): List<Venta>
}
