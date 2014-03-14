package com.philiprehberger.httpclient

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/** Configuration for the HTTP client. */
public class HttpClientConfig {
    public var baseUrl: String = ""
    public var timeout: Duration = 30.seconds
    public val defaultHeaders: MutableMap<String, String> = mutableMapOf()
}
