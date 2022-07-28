package com.aj.urlshortener.datatransferobject

import java.time.LocalDateTime

class UrlErrorResponseDto (
    val message: String?,
    val errorTime: LocalDateTime
)