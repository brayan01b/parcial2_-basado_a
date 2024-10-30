package com.example.parcial.DAO

import androidx.room.*
import com.example.parcial.Model.Producto

@Dao
interface ProductoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarProducto(producto: Producto)

    @Update
    suspend fun actualizarProducto(producto: Producto)

    @Delete
    suspend fun eliminarProducto(producto: Producto)

    @Query("SELECT * FROM productos WHERE id = :id")
    suspend fun obtenerProductoPorId(id: Int): Producto?

    @Query("SELECT * FROM productos")
    suspend fun obtenerTodosLosProductos(): List<Producto>
}
