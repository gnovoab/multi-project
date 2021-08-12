
//Namespace
package com.gnovoa.catalog.controller.handler

import com.gnovoa.catalog.domain.api.ApiErrorResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.function.Consumer
import javax.validation.ConstraintViolationException

/**
 * Class that handle common global exception handlers such as MessageNotReadableException, InvalidMethodArgumentException or RequestNotSupported
 */
@ControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {

    /**
     * MessageNotReadable handler
     */
    override fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val mostSpecificCause = ex.mostSpecificCause
        val apiErrorResponse =  ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.mostSpecificCause.message!!, listOf("Exception name: " + mostSpecificCause.javaClass.name))
        return ResponseEntity(apiErrorResponse, headers, status)
    }

    /**
     * MethodArgumentNotValid handler
     */
    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {

        val errors = arrayListOf<String>()

        for (error in ex.bindingResult.fieldErrors) {
            errors.add(error.field + ": " + error.defaultMessage)
        }

        for (error in ex.bindingResult.globalErrors) {
            errors.add(error.objectName + ": " + error.defaultMessage)
        }

        //Create and send the Error Message
        val apiErrorResponse = ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.localizedMessage, errors)
        return ResponseEntity<Any>(apiErrorResponse, HttpHeaders(), apiErrorResponse.status)
    }

    /**
     * MissingServletRequestParameter handler
     */
    override fun handleMissingServletRequestParameter(ex: MissingServletRequestParameterException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val error = ex.parameterName + " parameter is missing"

        //Create and send the Error Message
        val apiErrorResponse = ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.localizedMessage, listOf(error))
        return ResponseEntity<Any>(apiErrorResponse, HttpHeaders(), apiErrorResponse.status)
    }

    /**
     * HttpRequestMethodNotSupported handler
     */
    override fun handleHttpRequestMethodNotSupported(ex: HttpRequestMethodNotSupportedException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {

        val builder = StringBuilder()
        builder.append(ex.method)
        builder.append(" method is not supported for this request. Supported methods are ")
        ex.supportedHttpMethods!!.forEach(Consumer { t: HttpMethod -> builder.append("$t ")})

        //Create and send the Error Message
        val apiErrorResponse = ApiErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, ex.localizedMessage, listOf(builder.toString()))
        return ResponseEntity<Any>(apiErrorResponse, HttpHeaders(), apiErrorResponse.status)
    }

    /**
     *  NoHandlerFoundException handler
     */
    override fun handleNoHandlerFoundException(ex: NoHandlerFoundException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        val error = "No handler found for " + ex.httpMethod + " " + ex.requestURL
        val apiErrorResponse = ApiErrorResponse(HttpStatus.NOT_FOUND, ex.localizedMessage, listOf(error))
        return ResponseEntity<Any>(apiErrorResponse, HttpHeaders(), apiErrorResponse.status)
    }


    /**
     *  ConstraintViolationException handler
     */
    @Suppress("UnusedPrivateMember")
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation( ex:ConstraintViolationException, request: WebRequest ): ResponseEntity<Any>{
        val errors = arrayListOf<String>()

        for (violation in ex.constraintViolations) {
            errors.add(violation.rootBeanClass.name + " " + violation.propertyPath + ": " + violation.message)
        }

        val apiErrorResponse = ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.localizedMessage, errors)
        return ResponseEntity<Any>(apiErrorResponse, HttpHeaders(), apiErrorResponse.status)
    }

    /**
     *  MethodArgumentTypeMismatch handler
     */
    @Suppress("UnusedPrivateMember")
    @ExceptionHandler(value = [(MethodArgumentTypeMismatchException::class)])
    fun handleMethodArgumentTypeMismatch(ex: MethodArgumentTypeMismatchException, request: WebRequest): ResponseEntity<Any>{
        val error = ex.name + " should be of type " + ex.requiredType!!.name
        val apiErrorResponse = ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.localizedMessage, listOf(error))
        return ResponseEntity<Any>(apiErrorResponse, HttpHeaders(), apiErrorResponse.status)
    }
}
