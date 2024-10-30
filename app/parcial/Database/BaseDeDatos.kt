package com.example.parcial.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.parcial.DAO.ClienteDao
import com.example.parcial.DAO.ProductoDao
import com.example.parcial.DAO.VentaDao
import com.example.parcial.Model.Cliente
import com.example.parcial.Model.Producto
import com.example.parcial.Model.Venta

@Database(entities = [Cliente::class, Producto::class, Venta::class], version = 2, exportSchema = false)
abstract class BasedeDatos : RoomDatabase() {

    abstract fun clienteDao(): ClienteDao
    abstract fun productoDao(): ProductoDao
    abstract fun ventaDao():   VentaDao

    companion object {
        @Volatile
        private var INSTANCE: BasedeDatos? = null

        fun getDatabase(context: Context): BasedeDatos {
            return INSTANCE ?: synchronized(this) {
                val instance =  Room.databaseBuilder(
                    context.applicationContext,
                    BasedeDatos::class.java,
                    "base_de_datos"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}