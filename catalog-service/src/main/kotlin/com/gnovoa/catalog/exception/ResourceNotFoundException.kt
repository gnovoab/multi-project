package com.gnovoa.catalog.exception

/**
 * Class that represents an exception when a resource is not found
 */
class ResourceNotFoundException: RuntimeException{
    constructor(message: String): super(message) {}
    constructor(message: String, ex: Exception?): super(message, ex) {}
    constructor(ex: Exception): super(ex) {}
}
