package com.aj.urlshortener.datatransferobject

import java.time.LocalDateTime

class UrlResponseDto (
    val longUrl: String,
    val shortUrl: String,
    val expirationDate: LocalDateTime
)