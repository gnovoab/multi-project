package com.gnovoa.catalog.controller.product

import com.gnovoa.catalog.domain.model.Product
import com.gnovoa.catalog.domain.rest.product.CreateProductRequest
import com.gnovoa.catalog.factory.ObjectFactory
import org.apache.commons.lang3.RandomStringUtils
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
class CreateProductEndpoint (@Autowired val restTemplate: TestRestTemplate){

    companion object {
        const val BASE_URL = "/api/v1/products"
    }

    @Test
    internal fun saveProductOK() {

        //Create payload
        val productRequestPayload = CreateProductRequest(
           ObjectFactory.generateSampleProduct(),
           RandomStringUtils.randomNumeric(1,3).toInt()
        )


        //Set the headers
        val requestHeaders = HttpHeaders()
        requestHeaders.contentType = MediaType.APPLICATION_JSON

        //Create the http request
        val request: HttpEntity<*> = HttpEntity<Any>(productRequestPayload, requestHeaders)

        //Invoke the API service
        val response = restTemplate.exchange<Product>(BASE_URL, HttpMethod.POST, request, object : ParameterizedTypeReference<Product?>() {})

        //Verify
        Assertions.assertEquals(HttpStatus.CREATED, response.statusCode)
        Assertions.assertNotNull(response.body)
        Assertions.assertTrue(response.body!!.name!!.isNotEmpty())
        Assertions.assertTrue(response.body!!.price!!.toDouble() > 0)
    }

}
