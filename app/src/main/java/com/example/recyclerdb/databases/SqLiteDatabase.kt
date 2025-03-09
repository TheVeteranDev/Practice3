package com.example.recyclerdb.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recyclerdb.products.Product
import com.example.recyclerdb.products.ProductDoa
import kotlin.jvm.java

@Database(entities = [Product::class], version = 7)
abstract class SqLiteDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDoa

    companion object {
        @Volatile
        private var INSTANCE: SqLiteDatabase? = null

        fun getDatabase(context: Context): SqLiteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SqLiteDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}