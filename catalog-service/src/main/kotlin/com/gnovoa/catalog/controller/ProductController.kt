package com.gnovoa.catalog.controller

import com.gnovoa.catalog.domain.api.ApiErrorResponse
import com.gnovoa.catalog.domain.api.ApiMessageResponse
import com.gnovoa.catalog.domain.model.Product
import com.gnovoa.catalog.domain.rest.CreateProductRequest
import com.gnovoa.catalog.service.ProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * Endpoint for Products
 */
@Tag(name = "PRODUCT", description = "Operations related to Product Catalog")
@RestController
@RequestMapping("/api/v1/products")
class ProductController(private val productService: ProductService) {

    /**
     * Fetch all products
     * @return
     */
    @Operation(summary = "Retrieve all Products", description = "Fetch all active and inactive products", tags = ["product"])
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Successful operation",
                content = [(Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = (ArraySchema(schema = Schema(implementation = Product::class)))
                ))]
            ),
            ApiResponse(
                responseCode = "500", description = "The service encountered a problem.",
                content = [(Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ApiErrorResponse::class)
                ))]
            )
        ]
    )
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun fetchAllProducts(): ResponseEntity<Iterable<Product>> {

        //Retrieve products
        val products:Iterable<Product> = productService.fetchProducts()

        //Return the products
        return ResponseEntity(products, HttpStatus.OK)
    }


    /**
     * Fetch active products
     * @return
     */
    @Operation(summary = "Retrieve active products only", description = "Fetch active products", tags = ["product"])
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200", description = "Successful operation",
                content = [(Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = (ArraySchema(schema = Schema(implementation = Product::class)))
                ))]
            ),
            ApiResponse(
                responseCode = "500", description = "The service encountered a problem.",
                content = [(Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ApiErrorResponse::class)
                ))]
            )
        ]
    )
    @GetMapping(value = ["/active"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun fetchActiveProducts(): ResponseEntity<Iterable<Product>> {

        //Retrieve products
        val products:Iterable<Product> = productService.fetchActiveProducts()

        //Return the products
        return ResponseEntity(products, HttpStatus.OK)
    }


    /**
     * Get specific product
     * @param id
     * @return
     */
    @Operation(summary = "Retrieve a product", description = "Gives you a product from a given id", tags = ["product"])
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful operation",
                content = [(Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = Product::class)
                ))]
            ),
            ApiResponse(responseCode = "400", description = "Malformed request syntax",
                content = [(Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = ApiErrorResponse::class)
                ))]
            ),
            ApiResponse(responseCode = "404", description = "Resource not found",
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
    @GetMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun fetchProduct(@PathVariable @Valid id:Long): ResponseEntity<Product> {

        //Retrieve products
        val product:Product = productService.findProduct(id)

        //Return the products
        return ResponseEntity(product, HttpStatus.OK)
    }


    /**
     * Create a product
     */
    @Operation(summary = "Create a product", description = "Inserts a new product into DB", tags = ["product"])
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Resource created",
                content = [(Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = Product::class)
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
    fun createProduct(@RequestBody @Valid request:CreateProductRequest): ResponseEntity<Product> {

        //Create product
        val product:Product = productService.save(request.product)

        //Return the products
        return ResponseEntity(product, HttpStatus.CREATED)
    }

    /**
     * Updates a product
     * @param id
     * @return
     */
    @Operation(summary = "Update a product", description = "Change/Ammend core properties of a product", tags = ["product"])
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful operation",
                content = [(Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = Product::class)
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
    @PutMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateProduct(@PathVariable @Valid id:Long, @RequestBody @Valid product: Product): ResponseEntity<Product> {

        //Retrieve product
        val dbProduct:Product = productService.findProduct(id)

        //Assign fixed data
        product.id = dbProduct.id
        product.sku = dbProduct.sku
        product.uuid = dbProduct.uuid

        //Update product
        val productUpdated:Product = productService.save(product)

        //Return the products
        return ResponseEntity(productUpdated, HttpStatus.OK)
    }



    /**
     * Delete a product
     * @param id
     * @return
     */
    @Operation(summary = "Delete a product", description = "Deletes a product from a given id", tags = ["product"])
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Successful operation",
                content = [(Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = Schema(implementation = Product::class)
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
    @DeleteMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteProduct(@PathVariable @Valid id:Long): ResponseEntity<ApiMessageResponse> {

        //Delete product
        productService.delete(id)

        //Return the products
        return ResponseEntity(ApiMessageResponse(HttpStatus.OK, "Product Deleted"), HttpStatus.OK)
    }



}
