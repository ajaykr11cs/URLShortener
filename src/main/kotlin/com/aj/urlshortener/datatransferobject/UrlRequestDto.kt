package com.aj.urlshortener.datatransferobject

import java.time.LocalDateTime

class UrlRequestDto (
    val longUrl:String,
    val expirationDate: LocalDateTime?
)