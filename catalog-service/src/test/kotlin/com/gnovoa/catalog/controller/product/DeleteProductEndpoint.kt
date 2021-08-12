package com.gnovoa.catalog.controller.product

import com.gnovoa.catalog.domain.api.ApiMessageResponse
import com.gnovoa.catalog.domain.model.Product
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
import java.util.*

/**
 * Integration Test Class
 */
@Suppress("ClassOrdering")
@ActiveProfiles("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeleteProductEndpoint (@Autowired val restTemplate: TestRestTemplate,  @Autowired val productRepository: ProductRepository){

    companion object {
        const val BASE_URL = "/api/v1/products"
    }

    @Test
    internal fun deleteProductOK() {

        //Prepopulate DB
        val dbProduct = productRepository.save(ObjectFactory.generateSampleProduct())

        //Set the headers
        val requestHeaders = HttpHeaders()
        requestHeaders.contentType = MediaType.APPLICATION_JSON

        //Create the http request
        val request: HttpEntity<*> = HttpEntity<Any>(requestHeaders)

        //Invoke the API service
        val response = restTemplate.exchange<ApiMessageResponse>(BASE_URL + "/" + dbProduct.id, HttpMethod.DELETE, request, object : ParameterizedTypeReference<ApiMessageResponse?>() {})

        //Verify
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(HttpStatus.OK, response.body!!.status)
        Assertions.assertEquals("Product Deleted", response.body!!.message)

        val deletedProduct:Optional<Product> = productRepository.findById(dbProduct.id!!.toLong())
        Assertions.assertFalse(deletedProduct.isPresent)
    }
}
