package com.gnovoa.catalog.service

import com.gnovoa.catalog.domain.model.Product
import com.gnovoa.catalog.exception.ResourceNotFoundException
import com.gnovoa.catalog.factory.ObjectFactory
import com.gnovoa.catalog.respository.ProductRepository
import com.gnovoa.catalog.service.impl.ProductServiceImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doNothing
import org.mockito.kotlin.*
import org.springframework.test.context.ActiveProfiles
import java.util.*

/**
 * Unit Test Class
 */
@ActiveProfiles("unitTest")
class ProductServiceTest {

    @Test
    internal fun fetchProductsTest() {
        //Create Mock
        val productRepository : ProductRepository = mock()

        //Inject Mock
        val productService = ProductServiceImpl(productRepository)

        //Create object to be returned
        val products = arrayListOf(ObjectFactory.generateSampleProduct(), ObjectFactory.generateSampleProduct(), ObjectFactory.generateSampleProduct())

        //Set behaviour
        whenever(productRepository.findAll()).thenReturn(products)

        //Execute
        productService.fetchProducts()

        //Verify
        verify(productRepository, times(1)).findAll()
    }

    @Test
    internal fun fetchActiveProductsTest() {
        //Create Mock
        val productRepository : ProductRepository = mock()

        //Inject Mock
        val productService = ProductServiceImpl(productRepository)

        //Create object to be returned
        val products = arrayListOf(ObjectFactory.generateSampleProduct(), ObjectFactory.generateSampleProduct(), ObjectFactory.generateSampleProduct())

        //Set behaviour
        whenever(productRepository.findByActiveTrue()).thenReturn(products)

        //Execute
        productService.fetchActiveProducts()

        //Verify
        verify(productRepository, times(1)).findByActiveTrue()
    }

    @Test
    internal fun findProductByIdOkTest() {
        //Create Mock
        val productRepository : ProductRepository = mock()

        //Inject Mock
        val productService = ProductServiceImpl(productRepository)

        //Set behaviour
        whenever(productRepository.findById(any())).thenReturn(Optional.of(Product()))

        //Execute
        productService.findProduct(1L)

        //Verify
        verify(productRepository, times(1)).findById(any())
    }


    @Test
    internal fun findProductByIdFailedTest() {
        //Create Mock
        val productRepository : ProductRepository = mock()

        //Inject Mock
        val productService = ProductServiceImpl(productRepository)

        //Set behaviour
        whenever(productRepository.findById(any())).thenReturn(Optional.empty())

        //Execute
        Assertions.assertThrows(ResourceNotFoundException::class.java) {
            productService.findProduct(0)
        }

        //Verify
        verify(productRepository, times(1)).findById(any())
    }

    @Test
    internal fun createProduct() {
        //Create Mock
        val productRepository : ProductRepository = mock()

        //Inject Mock
        val productService = ProductServiceImpl(productRepository)

        //Create payload
        val product = ObjectFactory.generateSampleProduct()

        //Set behaviour
        whenever(productRepository.save(product)).thenReturn(Product())

        //Execute
        productService.save(product)

        //Verify
        verify(productRepository, times(1)).save(any())
    }


    @Test
    internal fun deleteProduct() {
        //Create Mock
        val productRepository : ProductRepository = mock()

        //Inject Mock
        val productService = ProductServiceImpl(productRepository)

        //Set behaviour
        doNothing().whenever(productRepository).deleteById(anyVararg())

        //Execute
        productService.delete(1L)

        //Verify
        verify(productRepository, times(1)).deleteById(any())
    }
}
