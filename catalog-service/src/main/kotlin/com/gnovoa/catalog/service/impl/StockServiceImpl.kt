package com.gnovoa.catalog.service.impl

import com.gnovoa.catalog.client.StockClient
import com.gnovoa.catalog.domain.model.Product
import com.gnovoa.catalog.domain.rest.stock.CreateProductStockRequest
import com.gnovoa.catalog.domain.rest.stock.CreateProductStockResponse
import com.gnovoa.catalog.service.StockService
import org.springframework.stereotype.Service

/**
 * Class hat handles operations regarding products
 */
@Service
class StockServiceImpl(private val stockClient: StockClient): StockService{

    override fun createProductStock(product: Product, quantity: Int): CreateProductStockResponse {

        //Create payload
        val request = CreateProductStockRequest(product.id, quantity)

        return stockClient.createProductStock(request)
    }
}
