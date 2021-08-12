package com.gnovoa.catalog.domain.rest

import com.gnovoa.catalog.domain.model.Product
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

/**
 * Class that represents the request payload for create a Product
 */
class CreateProductRequest(
    @NotNull val product: Product,
    @NotNull @Positive val quantity:Int
) {

}
