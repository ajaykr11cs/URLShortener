package com.aj.urlshortener.repository

import com.aj.urlshortener.model.Url
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UrlRepository : MongoRepository<Url, String>  {

    fun findByShortUrl(url: String): Optional<Url>
}