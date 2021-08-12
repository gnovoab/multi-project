package com.gnovoa.stock.service

import com.gnovoa.stock.factory.ObjectFactory
import com.gnovoa.stock.repository.StockRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

/**
 * Unit test class
 */
@ActiveProfiles("unitTest")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StockServiceTest(@Autowired val stockService: StockService, @Autowired val stockRepository: StockRepository) {

    @Test
    internal fun saveTest() {
        val rowsBefore = stockRepository.count()
        stockService.save(ObjectFactory.generateSampleProductStock())
        val rowsAfter = stockRepository.count()
        Assertions.assertEquals(rowsBefore + 1, rowsAfter)
    }
}
