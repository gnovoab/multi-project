package com.gnovoa.catalog.controller.handler

import com.gnovoa.catalog.domain.api.ApiErrorResponse
import com.gnovoa.catalog.exception.ResourceNotFoundException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class CustomResponseEntityExceptionHandler {

    @Suppress("UnusedPrivateMember")
    @ExceptionHandler(value = [ResourceNotFoundException::class])
    fun exceptionHandler(e: ResourceNotFoundException, request: WebRequest?): ResponseEntity<ApiErrorResponse?>? {
        return ResponseEntity(ApiErrorResponse(HttpStatus.NOT_FOUND, e.message!!), HttpStatus.NOT_FOUND)
    }
}
