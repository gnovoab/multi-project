package com.gnovoa.stock.service.impl

import com.gnovoa.stock.domain.model.ProductStock
import com.gnovoa.stock.repository.StockRepository
import com.gnovoa.stock.service.StockService
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val LOGGER = KotlinLogging.logger {}

/**
 * Class hat handles operations regarding stock for products
 */
@Service
class StockServiceImpl(private val stockRepository: StockRepository): StockService {

    override fun save(productStock: ProductStock): ProductStock {
        LOGGER.info { "Saving/Updating product [${productStock.productId}] into DB..."}
        val productSaved = stockRepository.save(productStock)
        LOGGER.info { "Product [${productStock.productId}] saved/updated"}

        return productSaved
    }
}
