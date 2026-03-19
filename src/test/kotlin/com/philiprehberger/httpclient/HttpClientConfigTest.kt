package com.philiprehberger.httpclient

import kotlin.test.*
import kotlin.time.Duration.Companion.seconds

class HttpClientConfigTest {
    @Test fun `default config`() {
        val cfg = HttpClientConfig()
        assertEquals("", cfg.baseUrl)
        assertEquals(30.seconds, cfg.timeout)
    }
    @Test fun `custom config`() {
        val client = httpClient {
            baseUrl = "https://api.example.com"
            timeout = 10.seconds
            defaultHeaders["Authorization"] = "Bearer token"
        }
        assertNotNull(client)
    }
}
