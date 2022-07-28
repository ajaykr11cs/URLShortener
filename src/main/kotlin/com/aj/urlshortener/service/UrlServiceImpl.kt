package com.aj.urlshortener.service

import com.aj.urlshortener.datatransferobject.UrlRequestDto
import com.aj.urlshortener.exception.EmptyInputException
import com.aj.urlshortener.exception.ExpiredUrlException
import com.aj.urlshortener.exception.InvalidInputException
import com.aj.urlshortener.exception.UrlNotFoundException
import com.aj.urlshortener.model.Url
import com.aj.urlshortener.repository.UrlRepository
import com.google.common.hash.Hashing
import org.apache.commons.validator.routines.UrlValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime

@Service
class UrlServiceImpl constructor ( @Autowired val urlRepository: UrlRepository ) : UrlService {

    override fun createShortUrl(urlRequestDto: UrlRequestDto): Url {
        // Need to check the validity of user data and then further process
        if( urlRequestDto.longUrl.isNullOrBlank() ) throw EmptyInputException( message = "Url is empty")

        val urlValidator = UrlValidator()
        if(!urlValidator.isValid(urlRequestDto.longUrl)) throw InvalidInputException(message = "Url is invalid")

        if( urlRequestDto.expirationDate != null ){
            val expirationDate: LocalDateTime = urlRequestDto.expirationDate
            if( expirationDate.isBefore(LocalDateTime.now()))
                throw InvalidInputException("Expiration Date cannot be in past.")
        }

        //To generate shortUrl from the provided Long Url
        val encodedUrl = encodeUrl(urlRequestDto.longUrl)
        val updatedUrl = Url(longUrl = urlRequestDto.longUrl,
            shortUrl = encodedUrl,
            expirationDate = getExpirationDate(urlRequestDto.expirationDate, LocalDateTime.now()))

        return persistShortUrl(updatedUrl)
    }

    override fun persistShortUrl(url: Url): Url {
        return urlRepository.save(url)
    }

    @Cacheable(value= ["Url"], key="#shortUrl")
    override fun getLongUrl(shortUrl: String): String {
        if(shortUrl.isNullOrBlank()) throw EmptyInputException("URL is empty");

        println("Going to hit the database")
        val urlObject = urlRepository.findByShortUrl(shortUrl)
            .orElseThrow{ throw UrlNotFoundException("Url records not found in the database. Kindly generate short url and try again.") }

        if(urlObject.expirationDate.isBefore(LocalDateTime.now()))
            throw ExpiredUrlException("Url expired. Generate a new short url.")

        return urlObject.longUrl
    }

    fun encodeUrl(longUrl: String): String{
        val encodedUrl: String
        val currentTime = LocalDateTime.now()
        encodedUrl = Hashing.murmur3_32_fixed()
            .hashString( longUrl + currentTime.toString(), StandardCharsets.UTF_8).toString()
        return encodedUrl
    }

    fun getExpirationDate(expirationDate: LocalDateTime?, creationDate: LocalDateTime): LocalDateTime{
        if (expirationDate == null) {
             return creationDate.plusDays(30)
            }else{
                return expirationDate
        }
    }


}