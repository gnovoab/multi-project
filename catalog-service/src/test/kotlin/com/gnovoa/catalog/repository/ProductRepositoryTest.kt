package com.gnovoa.catalog.repository

import com.gnovoa.catalog.domain.model.Product
import com.gnovoa.catalog.factory.ObjectFactory
import com.gnovoa.catalog.respository.ProductRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

/**
 * Integration Test Class
 */
@ActiveProfiles("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductRepositoryTest(@Autowired val productRepository: ProductRepository) {

    @Test
    internal fun createtAndUpdateTest() {
        var product: Product = ObjectFactory.generateSampleProduct()

        //Verify Created Date
        product = productRepository.save(product)
        Assertions.assertNotNull(product.createdDate)
        Assertions.assertNull(product.lastModified)

        //Verify Last Modified
        product.picture = "sample"
        product = productRepository.save(product)
        Assertions.assertNotNull(product.createdDate)
        Assertions.assertNotNull(product.lastModified)
    }

    @Test
    internal fun findActiveTest() {

        //Get number of records
        val productsCount = productRepository.count()
        val productsActiveCount = productRepository.findByActiveTrue().count()

        //Create Sample product
        var product: Product = ObjectFactory.generateSampleProduct()

        //Verify Active is set by default
        product = productRepository.save(product)
        Assertions.assertTrue(product.active)

        //Verify Not Active set up
        product = ObjectFactory.generateSampleProduct()
        product.active = false

        product = productRepository.save(product)
        Assertions.assertFalse(product.active)

        //Verify count
        Assertions.assertEquals(productsActiveCount + 1, productRepository.findByActiveTrue().count())
        Assertions.assertEquals(productsCount + 2, productRepository.count())
    }


}
