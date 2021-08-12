package com.gnovoa.stock.repository

import com.gnovoa.stock.domain.model.ProductStock
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface StockRepository: CrudRepository<ProductStock, Long> {
}
