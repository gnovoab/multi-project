package com.gnovoa.stock.controller

import com.gnovoa.stock.domain.model.ProductStock
import com.gnovoa.stock.factory.ObjectFactory
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
class StockControllerEndpointTest(@Autowired val restTemplate: TestRestTemplate) {
    companion object {
        const val BASE_URL = "/api/v1/stock"
    }

    @Test
    internal fun saveProductOK() {

        //Create payload
        val productStockRequestPayload = ObjectFactory.generateSampleProductStock()

        //Set the headers
        val requestHeaders = HttpHeaders()
        requestHeaders.contentType = MediaType.APPLICATION_JSON

        //Create the http request
        val request: HttpEntity<*> = HttpEntity<Any>(productStockRequestPayload, requestHeaders)

        //Invoke the API service
        val response = restTemplate.exchange<ProductStock>(BASE_URL, HttpMethod.POST, request, object : ParameterizedTypeReference<ProductStock?>() {})

        //Verify
        Assertions.assertEquals(HttpStatus.CREATED, response.statusCode)
        Assertions.assertNotNull(response.body)
        Assertions.assertTrue(response.body!!.productId!! < 0)
        Assertions.assertTrue(response.body!!.quantity!! > 0)
    }
}
