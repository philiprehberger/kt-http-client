package com.philiprehberger.httpclient

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/** Builder for HTTP request configuration. */
public class RequestBuilder {
    @PublishedApi internal val headers: MutableMap<String, String> = mutableMapOf()
    @PublishedApi internal val queryParams: MutableList<Pair<String, String>> = mutableListOf()
    @PublishedApi internal var bodyContent: String? = null
    @PublishedApi internal var contentType: String = "application/json"

    /** Add a header. */
    public fun header(name: String, value: String) { headers[name] = value }
    /** Add a query parameter. */
    public fun query(key: String, value: String) { queryParams.add(key to value) }
    /** Set JSON body. */
    public inline fun <reified T> json(body: T) { bodyContent = Json.encodeToString(body); contentType = "application/json" }
    /** Set raw body. */
    public fun body(content: String, contentType: String = "text/plain") { bodyContent = content; this.contentType = contentType }
    /** Set form-encoded body. */
    public fun form(vararg pairs: Pair<String, String>) {
        bodyContent = pairs.joinToString("&") { "${java.net.URLEncoder.encode(it.first, "UTF-8")}=${java.net.URLEncoder.encode(it.second, "UTF-8")}" }
        contentType = "application/x-www-form-urlencoded"
    }
}
