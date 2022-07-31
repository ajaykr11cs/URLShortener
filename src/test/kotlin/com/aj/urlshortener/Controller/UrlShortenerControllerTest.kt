package com.aj.urlshortener.Controller

import com.aj.urlshortener.datatransferobject.UrlRequestDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
internal class UrlShortenerControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
){
    val baseUrl = "/api/v1/url"

    @Nested
    @DisplayName("POST /api/v1/url/create")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostShortUrl{
        @Test
        fun `should create new short Url`(){

            //given
            val newUrlRequestDto = UrlRequestDto("http://www.google.com",
                LocalDateTime.parse("2022-08-26T19:37:59.557") )

            //then
            /* only checking for successful creation response as  everytime it will return a
                newly generated short url.
             */
            mockMvc.post("$baseUrl/create") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newUrlRequestDto)
                }
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                }
        }

        @Test
        fun `should return BAD REQUEST if url with empty longUrl provided` (){
            //given
            val newUrlRequestDto = UrlRequestDto("  ",
                LocalDateTime.parse("2022-08-26T19:37:59.557") )

            mockMvc.post("$baseUrl/create") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newUrlRequestDto)
            }
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }

        @Test
        fun `should return BAD REQUEST if url with invalid longUrl provided` (){
            //given
            val newUrlRequestDto = UrlRequestDto("ww.",
                LocalDateTime.parse("2022-08-26T19:37:59.557") )

            mockMvc.post("$baseUrl/create") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newUrlRequestDto)
            }
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }

        @Test
        fun `should return BAD REQUEST if url with invalid expirationDate provided` (){
            //given
            val newUrlRequestDto = UrlRequestDto("http://www.google.com",
                LocalDateTime.parse("2022-07-26T19:37:59.557") )

            mockMvc.post("$baseUrl/create") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newUrlRequestDto)
            }
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }
    }

    @Nested
    @DisplayName("GET /api/v1/url/{shortUrl}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetLongUrl {

        @Test
        fun `should return longUrl corresponding to shortUrl`(){
            //given
            val shortUrl = "f8ff5ab0"
            val longUrl = "http://www.google.com"
            //when
            val result = mockMvc.get("$baseUrl/$shortUrl")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                }
                .andReturn()
                .response.contentAsString.equals(longUrl)
        }

        @Test
        fun `should return NOT FOUND if shortUrl doesn't exists`(){
            //given
            val shortUrl = "f8ff5ab021"
            //when
            mockMvc.get("$baseUrl/$shortUrl")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }

        @Test
        fun `should return BAD REQUEST if shortUrl is empty`(){
            //given
            val shortUrl = "  "
            //when
            mockMvc.get("$baseUrl/$shortUrl")
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }

        @Test
        fun `should return BAD REQUEST if shortUrl is expired`(){
            //given
            val shortUrl = "5abd77b4"
            //when
            mockMvc.get("$baseUrl/$shortUrl")
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }
    }
}