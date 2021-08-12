package com.gnovoa.catalog.controller.product

import com.gnovoa.catalog.domain.model.Product
import com.gnovoa.catalog.factory.ObjectFactory
import com.gnovoa.catalog.respository.ProductRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles

/**
 * Integration Test Class
 */
@Suppress("ClassOrdering")
@ActiveProfiles("integrationTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FetchProductsEndpointTest(
    @Autowired val restTemplate: TestRestTemplate,
    @Autowired val productRepository: ProductRepository) {

    companion object {
        const val BASE_URL = "/api/v1/products"
    }

    @Test
    internal fun fetchProductsTest() {

        //Set the headers
        val requestHeaders = HttpHeaders()
        requestHeaders.contentType = MediaType.APPLICATION_JSON

        //Create the http request
        val request: HttpEntity<*> = HttpEntity<Any>(requestHeaders)

        //Invoke the API service
        val response: ResponseEntity<List<Product>> = restTemplate.exchange<List<Product>>(
                BASE_URL,
                HttpMethod.GET,
                request,
                object : ParameterizedTypeReference<List<Product?>?>() {})

        //Verify
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertNotNull(response.body)
    }


    @Test
    internal fun fetchActiveProductsTest() {

        //Get total of Rows
        val totalProductsInitial = productRepository.findByActiveTrue().count()

        //Prepopulate DB
        val product1: Product = ObjectFactory.generateSampleProduct()
        val product2: Product = ObjectFactory.generateSampleProduct()
        product2.active = false

        productRepository.save(product1)
        productRepository.save(product2)

        //Set the headers
        val requestHeaders = HttpHeaders()
        requestHeaders.contentType = MediaType.APPLICATION_JSON

        //Create the http request
        val request: HttpEntity<*> = HttpEntity<Any>(requestHeaders)

        //Invoke the API service
        val response = restTemplate.exchange<List<Product>>(
                BASE_URL + "/active",
                HttpMethod.GET,
                request,
                object : ParameterizedTypeReference<List<Product?>?>() {})

        //Verify
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertNotNull(response.body)
        Assertions.assertEquals((totalProductsInitial + 1).toLong(), response.body!!.count().toLong())
    }


    @Test
    internal fun fetchProduct() {
        //Prepopulate DB
        val dbProduct = productRepository.save(ObjectFactory.generateSampleProduct())

        //Set the headers
        val requestHeaders = HttpHeaders()
        requestHeaders.contentType = MediaType.APPLICATION_JSON

        //Create the http request
        val request: HttpEntity<*> = HttpEntity<Any>(requestHeaders)

        //Invoke the API service
        val response = restTemplate.exchange<Product>(BASE_URL + "/" + dbProduct.id, HttpMethod.GET, request, object : ParameterizedTypeReference<Product?>() {})

        //Verify
        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertNotNull(response.body)
        Assertions.assertEquals(dbProduct.id, response.body!!.id)
        Assertions.assertEquals(dbProduct.name, response.body!!.name)
        Assertions.assertEquals(dbProduct.price!!.toDouble(), response.body!!.price!!.toDouble())
        Assertions.assertEquals(dbProduct.picture, response.body!!.picture)
        Assertions.assertEquals(dbProduct.active, response.body!!.active)

    }


    @Test
    internal fun fetchProductDontExist() {
        //Set the headers
        val requestHeaders = HttpHeaders()
        requestHeaders.contentType = MediaType.APPLICATION_JSON

        //Create the http request
        val request: HttpEntity<*> = HttpEntity<Any>(requestHeaders)

        //Invoke the API service
        val response = restTemplate.exchange<Any>(BASE_URL + "/" + -1, HttpMethod.GET, request, object : ParameterizedTypeReference<Any?>() {})

        //Verify
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }


}
