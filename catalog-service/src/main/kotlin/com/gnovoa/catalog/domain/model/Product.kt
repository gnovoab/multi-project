package com.gnovoa.catalog.domain.model

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.constraints.*

@Entity
class Product (
    //Fields
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(unique = true)
    var uuid: UUID? = null,

    @NotBlank(message = "SKU is required.")
    @Column(unique = true, updatable = false)
    @Size(min = 2, max = 14)
    var sku: String? = null,

    @NotBlank(message = "Product name is required.")
    @Basic(optional = false)
    var name: String? = null,

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    var price: BigDecimal? = null,

    var picture: String? = null,
    var active: Boolean = true,

    @Column(name = "created_date", nullable = false, updatable = false)
    var createdDate: LocalDateTime? = null,

    @Column(name = "last_modified")
    var lastModified: LocalDateTime? = null
) {

    /**
     * Methods
     */

    @PrePersist
    fun onCreate(){
        createdDate = LocalDateTime.now()
        uuid = UUID.randomUUID()
    }

    @PreUpdate
    fun onUpdate(){
        lastModified = LocalDateTime.now()
    }
}
