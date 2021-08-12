package com.gnovoa.catalog.service.impl

import com.gnovoa.catalog.domain.model.Product
import com.gnovoa.catalog.exception.ResourceNotFoundException
import com.gnovoa.catalog.respository.ProductRepository
import com.gnovoa.catalog.service.ProductService
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val LOGGER = KotlinLogging.logger {}

/**
 * Class hat handles operations regarding products
 */
@Service
class ProductServiceImpl ( private val productRespository: ProductRepository): ProductService  {

    override fun fetchProducts(): Iterable<Product> {
        LOGGER.info { "Fetching active and inactive products..." }
        val products = productRespository.findAll()
        LOGGER.info { "Returning all active and inactive products" }
        return products
    }

    override fun fetchActiveProducts(): Iterable<Product> {
        LOGGER.info { "Fetching active products..." }
        val products = productRespository.findByActiveTrue()
        LOGGER.info { "Returning all active products" }
        return products
    }

    override fun findProduct(id: Long): Product {
        LOGGER.info { "Fetching product with id [$id]..." }
        val product = productRespository
            .findById(id)
            .orElseThrow {
                LOGGER.warn { "Product with id [$id] not found" }
                ResourceNotFoundException("Product with id [$id] not found")
            }
        LOGGER.info { "Returning product info with id [$id]"}

        return product
    }

    override fun save(product: Product): Product {
        LOGGER.info { "Saving/Updating product [${product.name}] into DB..."}
        val productSaved = productRespository.save(product)
        LOGGER.info { "Product [${product.name}] saved/updated"}

        return productSaved
    }

    override fun delete(id: Long) {
        LOGGER.info { "Deleting product with id [${id}] from DB..."}
        productRespository.deleteById(id)
        LOGGER.info { "Product [${id}] deleted"}
    }
}
