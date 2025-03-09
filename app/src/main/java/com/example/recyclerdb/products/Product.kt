package com.example.recyclerdb.products

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.net.URI

@Entity(tableName = "products", indices = [Index(value = ["name"], unique = true)])
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val description: String,
    val seller: String,
    val price: Double,
    val picture: Int,
    var checkoutId: Long = 0L
)

