package com.aj.urlshortener.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("url")
data class Url(
    @Id
    val shortUrl: String,
    val longUrl: String,
    val creationDate: LocalDateTime = LocalDateTime.now(),
    val expirationDate: LocalDateTime = LocalDateTime.now().plusDays(30)
) : java.io.Serializable