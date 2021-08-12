package com.gnovoa.stock.service

import com.gnovoa.stock.domain.model.ProductStock

interface StockService {
    fun save(productStock: ProductStock): ProductStock
}
