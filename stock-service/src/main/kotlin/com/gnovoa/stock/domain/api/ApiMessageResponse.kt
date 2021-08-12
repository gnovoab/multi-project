
//Namespace
package com.gnovoa.stock.domain.api

import org.springframework.http.HttpStatus

/**
 * Class that represents the response messages when a 3rd party interact with our service
 */

open class ApiMessageResponse (
    var status: HttpStatus,
    var message:String
    ) {


}
