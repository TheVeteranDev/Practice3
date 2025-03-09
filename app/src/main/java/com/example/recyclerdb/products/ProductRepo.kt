package com.example.recyclerdb.products

import kotlinx.coroutines.flow.Flow

class ProductRepo(private val productDoa: ProductDoa) {

    val products: Flow<List<Product>> = productDoa.list()
}