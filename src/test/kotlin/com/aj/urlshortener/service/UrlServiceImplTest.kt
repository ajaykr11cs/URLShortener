package com.aj.urlshortener.service

import com.aj.urlshortener.datatransferobject.UrlRequestDto
import com.aj.urlshortener.exception.*
import com.aj.urlshortener.model.Url
import com.aj.urlshortener.repository.UrlRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*

internal class UrlServiceImplTest{
    private val urlRepository: UrlRepository = mockk()
    private val urlService: UrlServiceImpl = UrlServiceImpl(urlRepository)

    private val aMockUrl = Url(
        "9283fbb4",
        "http://www.google.com",
        LocalDateTime.parse("2022-07-27T19:37:59.557"),
        LocalDateTime.parse("2022-08-27T19:37:59.557")
    )

    @Test
    fun `should create a short url`(){
        //given
        val newUrlRequestDto = UrlRequestDto(aMockUrl.longUrl,
            aMockUrl.expirationDate )

        every { urlRepository.save(any()) } returns aMockUrl

        //when
        val newUrl = urlService.createShortUrl(newUrlRequestDto)
        //val newUrl = urlRepository.save(aMockUrl)

        //then
        //verify( exactly = 1) { urlService.createShortUrl(newUrlRequestDto) } -> This will fail as every time a new shortUrl will be used and it won't match.
        assertNotNull(newUrl)
        assertNotNull(newUrl.shortUrl)
        assertEquals("9283fbb4", newUrl.shortUrl)
    }

    @Test
    fun `should throw InvalidInputException`(){
        //given
        val newUrlRequestDto = UrlRequestDto("ww.google.com",
            aMockUrl.expirationDate )
        every { urlRepository.save(any()) }.throws(InvalidInputException())

        //when
        val invalidException = assertThrows(InvalidInputException::class.java) { urlService.createShortUrl(newUrlRequestDto) }

        //verify
        assertNotNull(invalidException)
        assertNotNull(invalidException.message)
        assertEquals("Url is invalid", invalidException.message)
    }

    @Test
    fun `should throw InvalidInputException because of expirationDate in past`(){
        //given
        val newUrlRequestDto = UrlRequestDto(aMockUrl.longUrl,
            LocalDateTime.parse("2022-07-27T19:37:59.557") )
        every { urlRepository.save(any()) }.throws(InvalidInputException())

        //when
        val invalidException = assertThrows(InvalidInputException::class.java) { urlService.createShortUrl(newUrlRequestDto) }

        //verify
        assertNotNull(invalidException)
        assertNotNull(invalidException.message)
        assertEquals("Expiration Date cannot be in past", invalidException.message)
    }

    @Test
    fun `should throw EmptyInputException`(){
        //given
        val newUrlRequestDto = UrlRequestDto("",
            aMockUrl.expirationDate )
        every { urlRepository.save(any()) }.throws(EmptyInputException())

        //when
        val emptyException = assertThrows(EmptyInputException::class.java) { urlService.createShortUrl(newUrlRequestDto) }

        //verify
        assertNotNull(emptyException)
        assertNotNull(emptyException.message)
        assertEquals("Url is empty", emptyException.message)
    }

    @Test
    fun `should return corresponding long url `(){

        //Given
        val mockShortUrl = "9283fbb4"
        every { urlRepository.findByShortUrl(any()) } returns Optional.of(aMockUrl)

        //when
        val newUrl = urlService.getLongUrl(mockShortUrl)

        //verify
        verify( exactly = 1) { urlRepository.findByShortUrl(mockShortUrl) }
        assertNotNull(newUrl)
        assertEquals("http://www.google.com", newUrl)

    }

    @Test
    fun `should throw EmptyInputException for short url`(){

        //Given
        val mockShortUrl = ""
        every { urlService.getLongUrl(any()) }.throws(EmptyInputException())

        //when
        val emptyException = assertThrows(EmptyInputException::class.java) { urlService.getLongUrl(mockShortUrl) }

        //verify
        assertNotNull(emptyException)
        assertNotNull(emptyException.message)
        assertEquals("Url is empty", emptyException.message)

    }

}