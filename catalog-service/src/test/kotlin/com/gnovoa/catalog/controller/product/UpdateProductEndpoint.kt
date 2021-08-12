package com.gnovoa.catalog.controller.product

import com.gnovoa.catalog.domain.model.Product
import com.gnovoa.catalog.exception.ResourceNotFoundException
import com.gnovoa.catalog.factory.ObjectFactory
import com.gnovoa.catalog.respository.ProductRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles

/**
 * Integration Test Class
 */
@Suppress("ClassOrdering")
@ActiveProfiles("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UpdateProductEndpoint (@Autowired val restTemplate: TestRestTemplate,  @Autowired val productRepository: ProductRepository){

    companion object {
        const val BASE_URL = "/api/v1/products"
    }

    @Test
    internal fun updateProductOK() {
        //Prepopulate DB
        val originalProduct = productRepository.save(ObjectFactory.generateSampleProduct())

        //Create payload
        val productRequestPayload = ObjectFactory.generateSampleProduct()

        productRequestPayload.picture = ""
        productRequestPayload.active = false

        //Set the headers
        val requestHeaders = HttpHeaders()
        requestHeaders.contentType = MediaType.APPLICATION_JSON

        //Create the http request
        val request: HttpEntity<*> = HttpEntity<Any>(productRequestPayload, requestHeaders)

        //Invoke the API service
        val response = restTemplate.exchange<Product>(BASE_URL + "/" + originalProduct.id, HttpMethod.PUT, request, object : ParameterizedTypeReference<Product?>() {})

        //Verify
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)

        //Get the updated product from DB and validate
        val updatedProduct:Product = productRepository
            .findById(originalProduct.id!!.toLong())
            .orElseThrow({ResourceNotFoundException("Product  not found")})

        Assertions.assertEquals(originalProduct.id, updatedProduct.id)
        Assertions.assertEquals(originalProduct.sku, updatedProduct.sku)
        Assertions.assertEquals(productRequestPayload.name, updatedProduct.name)
        Assertions.assertEquals(productRequestPayload.price!!.toDouble(), updatedProduct.price!!.toDouble())
        Assertions.assertEquals(productRequestPayload.picture, updatedProduct.picture)
        Assertions.assertEquals(productRequestPayload.active, updatedProduct.active)
    }
}
