package com.example.recyclerdb.products

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDoa {

    @Query("SELECT * FROM products")
    fun list(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(product: Product)

    @Query("UPDATE products SET checkoutId = :checkoutId WHERE id = :productId")
    suspend fun updateCheckoutId(checkoutId: Long, productId: Long)

    @Query("SELECT * FROM products WHERE checkoutId = :checkoutId")
    fun listByCheckoutId(checkoutId: Long): Flow<List<Product>>
}