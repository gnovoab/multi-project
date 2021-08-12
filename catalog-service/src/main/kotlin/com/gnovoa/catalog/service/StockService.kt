package com.gnovoa.catalog.service

import com.gnovoa.catalog.domain.model.Product
import com.gnovoa.catalog.domain.rest.stock.CreateProductStockResponse

interface StockService {
    fun createProductStock(product: Product, quantity: Int): CreateProductStockResponse
}
