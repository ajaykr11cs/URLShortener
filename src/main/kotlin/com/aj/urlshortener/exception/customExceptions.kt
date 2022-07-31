package com.aj.urlshortener.exception

import java.time.LocalDateTime

class EmptyInputException(message: String = "Input is Empty") : RuntimeException(message) {
    val errorTime: LocalDateTime = LocalDateTime.now()
}

class InvalidInputException(message: String = "Input is invalid") : RuntimeException(message) {
    var errorTime: LocalDateTime = LocalDateTime.now()
}

class UrlNotFoundException(message: String = "Result not found") : RuntimeException(message) {
    var errorTime: LocalDateTime = LocalDateTime.now()
}

class ExpiredUrlException(message: String = "Url is expired") : RuntimeException(message) {
    var errorTime: LocalDateTime = LocalDateTime.now()
}