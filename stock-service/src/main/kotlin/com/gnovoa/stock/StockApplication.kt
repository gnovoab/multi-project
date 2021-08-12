package com.gnovoa.stock

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Main Class
 */
@SpringBootApplication
class StockApplication

fun main(args: Array<String>) {
	@Suppress("SpreadOperator")
	runApplication<StockApplication>(*args)
}
