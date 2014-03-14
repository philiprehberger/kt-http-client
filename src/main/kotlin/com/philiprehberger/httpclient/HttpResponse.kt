package com.philiprehberger.httpclient

import kotlinx.serialization.json.Json

/** HTTP response wrapper. */
public class HttpResponse(
    public val status: Int,
    public val body: String,
    public val headers: Map<String, List<String>>,
) {
    /** Deserialize body as JSON. */
    public inline fun <reified T> json(): T = Json.decodeFromString<T>(body)
    /** Check if status is 2xx. */
    public fun isSuccess(): Boolean = status in 200..299
}
