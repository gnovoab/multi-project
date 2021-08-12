package com.gnovoa.stock.controller

import com.gnovoa.stock.domain.api.ApiErrorResponse
import com.gnovoa.stock.domain.model.ProductStock
import com.gnovoa.stock.service.StockService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

/**
 * Endpoint for Products
 */
@Tag(name = "Stock", description = "Operations related to Product Stock availability")
@RestController
@RequestMapping("/api/v1/stock")
class StockController (private val stockService: StockService){

    /**
     * Create a product
     */
    @Operation(summary = "Create a product stock", description = "Inserts stock quantity for a given product into DB", tags = ["product"])
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Resource created",
                content = [(Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ProductStock::class)
                ))]
            ),
            ApiResponse(responseCode = "400", description = "Malformed request syntax",
                content = [(Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ApiErrorResponse::class)
                ))]
            ),
            ApiResponse( responseCode = "500", description = "The service encountered a problem.",
                content = [(Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ApiErrorResponse::class)
                ))]
            )
        ]
    )
    @PostMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createProduct(@RequestBody @Valid request:ProductStock): ResponseEntity<ProductStock> {

        //Create product
        val productStock:ProductStock = stockService.save(request)

        //Return the products
        return ResponseEntity(productStock, HttpStatus.CREATED)
    }
}
