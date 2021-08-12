package com.gnovoa.catalog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Main class
 */
@SpringBootApplication
class CatalogApplication

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<CatalogApplication>(*args)
}
