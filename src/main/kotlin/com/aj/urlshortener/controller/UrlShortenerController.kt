package com.aj.urlshortener.controller

import com.aj.urlshortener.datatransferobject.UrlRequestDto
import com.aj.urlshortener.datatransferobject.UrlResponseDto
import com.aj.urlshortener.service.UrlService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.IOException
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("api/v1/url")
class UrlShortenerController( @Autowired val urlService: UrlService) {

    @PostMapping("/create")
    fun createShortUrl( @RequestBody urlRequestDto: UrlRequestDto ): ResponseEntity<*>{
        val updatedUrl = urlService.createShortUrl(urlRequestDto)
        val response = UrlResponseDto(updatedUrl.longUrl, updatedUrl.shortUrl, updatedUrl.expirationDate)
        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @GetMapping("/{shortUrl}")
    @kotlin.jvm.Throws(IOException::class)
    fun getLongUrl( @PathVariable shortUrl: String, response: HttpServletResponse ) : ResponseEntity<*>? {
        val longUrl = urlService.getLongUrl(shortUrl)
        return ResponseEntity(longUrl, HttpStatus.OK)
    }

    @GetMapping("/redirect/{shortUrl}")
    @kotlin.jvm.Throws(IOException::class)
    fun redirectToLongUrl( @PathVariable shortUrl: String, response: HttpServletResponse ) : ResponseEntity<*>? {
        val longUrl = urlService.getLongUrl(shortUrl)
        response.sendRedirect(longUrl)
        return null
    }
}