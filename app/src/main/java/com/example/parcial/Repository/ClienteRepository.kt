package com.example.parcial.Repository

import com.example.parcial.DAO.ClienteDao
import com.example.parcial.Model.Cliente

class ClienteRepository(private val clienteDao: ClienteDao) {

    suspend fun insertarCliente(cliente: Cliente) {
        clienteDao.insertarCliente(cliente)
    }

    suspend fun actualizarCliente(cliente: Cliente) {
        clienteDao.actualizarCliente(cliente)
    }

    suspend fun eliminarCliente(cliente: Cliente) {
        clienteDao.eliminarCliente(cliente)
    }

    suspend fun obtenerClientePorId(id: Int): Cliente? {
        return clienteDao.obtenerClientePorId(id)
    }

    suspend fun obtenerTodosLosClientes(): List<Cliente> {
        return clienteDao.obtenerTodosLosClientes()
    }
}
