
//Namespace
package com.gnovoa.stock.factory

import com.gnovoa.stock.domain.model.ProductStock
import org.apache.commons.lang3.RandomStringUtils

class ObjectFactory private constructor() {

    @Suppress("MagicNumber")
    companion object {
        fun generateSampleProductStock(): ProductStock {
            val productSock = ProductStock()

            productSock.productId = RandomStringUtils.randomNumeric(6).toLong() *-1
            productSock.quantity = RandomStringUtils.randomNumeric(1,2).toInt()

            return productSock
        }

    }
}
