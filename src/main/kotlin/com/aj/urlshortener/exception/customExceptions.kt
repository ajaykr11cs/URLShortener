package com.aj.urlshortener.exception

import java.time.LocalDateTime

class EmptyInputException(message: String) : RuntimeException(message) {
    val errorTime: LocalDateTime = LocalDateTime.now()
}

class InvalidInputException(message: String) : RuntimeException(message) {
    var errorTime: LocalDateTime = LocalDateTime.now()
}

class UrlNotFoundException(message: String) : RuntimeException(message) {
    var errorTime: LocalDateTime = LocalDateTime.now()
}

class ExpiredUrlException(message: String) : RuntimeException(message) {
    var errorTime: LocalDateTime = LocalDateTime.now()
}