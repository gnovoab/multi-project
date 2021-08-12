package com.gnovoa.catalog.service

import com.gnovoa.catalog.domain.model.Product

interface ProductService {
    fun fetchProducts(): Iterable<Product>
    fun fetchActiveProducts(): Iterable<Product>
    fun findProduct(id: Long): Product
    fun save(product: Product): Product
    fun delete(id: Long)
}
