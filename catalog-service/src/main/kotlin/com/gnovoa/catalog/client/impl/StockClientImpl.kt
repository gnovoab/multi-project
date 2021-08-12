package com.gnovoa.catalog.client.impl

import com.gnovoa.catalog.client.StockClient
import com.gnovoa.catalog.domain.rest.stock.CreateProductStockRequest
import com.gnovoa.catalog.domain.rest.stock.CreateProductStockResponse
import org.springframework.stereotype.Component

@Component
class StockClientImpl: StockClient {

    override fun createProductStock(request: CreateProductStockRequest): CreateProductStockResponse {
        //Daniel Vocke to use his magic and call Stock Controller, method createProduct and pass the request argument as parameter
        return CreateProductStockResponse(request.productId, request.quantity)
    }
}
