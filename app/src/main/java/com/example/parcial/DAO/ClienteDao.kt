package com.example.parcial.DAO

import androidx.room.*
import com.example.parcial.Model.Cliente

@Dao
interface ClienteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCliente(cliente: Cliente)

    @Update
    suspend fun actualizarCliente(cliente: Cliente)

    @Delete
    suspend fun eliminarCliente(cliente: Cliente)

    @Query("SELECT * FROM clientes WHERE id = :id")
    suspend fun obtenerClientePorId(id: Int): Cliente?

    @Query("SELECT * FROM clientes")
    suspend fun obtenerTodosLosClientes(): List<Cliente>
}
