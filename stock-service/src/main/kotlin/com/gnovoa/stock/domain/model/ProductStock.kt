package com.gnovoa.stock.domain.model

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity
class ProductStock(
//Fields
@Id
@Column(name = "product")
var productId: Long? = null,

@Min(value = 0)
@NotNull
var quantity: Int? = null){

}
