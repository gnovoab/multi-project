package com.gnovoa.stock.domain.api

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus

/**
 * Class that represents the system response when an error ocur
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class ApiErrorResponse: ApiMessageResponse {

    var errors: List<String>? = null

    constructor(status: HttpStatus, message: String): super(status, message){
        super.status = status
        super.message = message
    }

    constructor(status: HttpStatus, message: String, errors: List<String>): super(status, message) {
        this.errors = errors
        super.status = status
        super.message = message
    }


}
