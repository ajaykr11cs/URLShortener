package com.aj.urlshortener.exception

import com.aj.urlshortener.datatransferobject.UrlErrorResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ControllerExceptionHandler : ResponseEntityExceptionHandler() {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmptyInputException::class)
    fun  handleEmptyInputException(exception: EmptyInputException?) : ResponseEntity<UrlErrorResponseDto>{
        val errorResponse = exception?.let { UrlErrorResponseDto(exception.message, it.errorTime) }
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidInputException::class)
    fun  handleInvalidInputException(exception: InvalidInputException?) : ResponseEntity<UrlErrorResponseDto>{
        val errorResponse = exception?.let { UrlErrorResponseDto(exception.message, it.errorTime) }
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UrlNotFoundException::class)
    fun  handleUrlNotFoundException(exception: UrlNotFoundException?) : ResponseEntity<UrlErrorResponseDto>{
        val errorResponse = exception?.let { UrlErrorResponseDto(exception.message, it.errorTime) }
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ExpiredUrlException::class)
    fun  handleExpiredUrlException(exception: ExpiredUrlException?) : ResponseEntity<UrlErrorResponseDto>{
        val errorResponse = exception?.let { UrlErrorResponseDto(exception.message, it.errorTime) }
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

}