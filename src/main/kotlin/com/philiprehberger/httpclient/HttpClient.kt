package com.philiprehberger.httpclient

import kotlinx.coroutines.future.await
import java.net.URI
import java.net.http.HttpRequest as JdkRequest
import java.net.http.HttpResponse as JdkResponse
import java.net.http.HttpClient as JdkClient
import java.time.Duration as JdkDuration

/** Create an HTTP client. */
public fun httpClient(block: HttpClientConfig.() -> Unit = {}): HttpClient {
    val cfg = HttpClientConfig().apply(block)
    return HttpClient(cfg)
}

/** Lightweight HTTP client with DSL. */
public class HttpClient internal constructor(private val config: HttpClientConfig) {
    private val jdkClient: JdkClient = JdkClient.newBuilder()
        .connectTimeout(JdkDuration.ofMillis(config.timeout.inWholeMilliseconds))
        .build()

    public suspend fun get(path: String, block: RequestBuilder.() -> Unit = {}): HttpResponse = request(HttpMethod.GET, path, block)
    public suspend fun post(path: String, block: RequestBuilder.() -> Unit = {}): HttpResponse = request(HttpMethod.POST, path, block)
    public suspend fun put(path: String, block: RequestBuilder.() -> Unit = {}): HttpResponse = request(HttpMethod.PUT, path, block)
    public suspend fun delete(path: String, block: RequestBuilder.() -> Unit = {}): HttpResponse = request(HttpMethod.DELETE, path, block)

    /** Execute an HTTP request. */
    public suspend fun request(method: HttpMethod, path: String, block: RequestBuilder.() -> Unit = {}): HttpResponse {
        val rb = RequestBuilder().apply(block)
        var url = config.baseUrl.trimEnd('/') + "/" + path.trimStart('/')
        if (rb.queryParams.isNotEmpty()) {
            url += "?" + rb.queryParams.joinToString("&") { "${it.first}=${it.second}" }
        }

        val builder = JdkRequest.newBuilder().uri(URI.create(url))
            .timeout(JdkDuration.ofMillis(config.timeout.inWholeMilliseconds))

        for ((k, v) in config.defaultHeaders) builder.header(k, v)
        for ((k, v) in rb.headers) builder.header(k, v)

        val bodyPublisher = if (rb.bodyContent != null) {
            builder.header("Content-Type", rb.contentType)
            JdkRequest.BodyPublishers.ofString(rb.bodyContent!!)
        } else {
            JdkRequest.BodyPublishers.noBody()
        }

        builder.method(method.name, bodyPublisher)

        val resp = jdkClient.sendAsync(builder.build(), JdkResponse.BodyHandlers.ofString()).await()
        val responseHeaders = resp.headers().map().mapValues { it.value }
        val httpResp = HttpResponse(resp.statusCode(), resp.body() ?: "", responseHeaders)
        if (!httpResp.isSuccess()) throw HttpException(httpResp.status, httpResp.body)
        return httpResp
    }
}
