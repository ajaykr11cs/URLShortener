# URLShortener
It is a simple app based on Spring Boot, MongoDb using Kotlin.

Following are the three REST api's which have been implmented :

1) /api/v1/url/create -> It will create a unique identifier for the short url and return and Url object
2) /api/v1/url/{shortUrl} -> It will return the longUrl based on the provided shortUrl
3) /api/v1/url/redirect/{shortUrl} -> It will redirect the URL to longUrl

Redis cache has been used to store the recently fetched shortUrl.
Since, it is a minimal implementation, there is no eviction from Cache. It will evict following the default way.


