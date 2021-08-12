
package com.gnovoa.catalog.respository

import com.gnovoa.catalog.domain.model.Product
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: CrudRepository<Product, Long> {
    fun findByActiveTrue(): Iterable<Product>
}
