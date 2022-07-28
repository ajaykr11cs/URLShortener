package com.aj.urlshortener.service

import com.aj.urlshortener.datatransferobject.UrlRequestDto
import com.aj.urlshortener.model.Url

interface UrlService {
    fun createShortUrl(urlRequestDto: UrlRequestDto): Url
    fun persistShortUrl(url: Url): Url
    fun getLongUrl(shortUrl: String): String
}