package com.gnovoa.catalog.client

import com.gnovoa.catalog.domain.rest.stock.CreateProductStockRequest
import com.gnovoa.catalog.domain.rest.stock.CreateProductStockResponse

interface StockClient {
    fun createProductStock(request: CreateProductStockRequest): CreateProductStockResponse
}
