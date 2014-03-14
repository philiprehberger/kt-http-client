package com.philiprehberger.httpclient

import kotlin.test.*

class RequestBuilderTest {
    @Test fun `query params`() {
        val rb = RequestBuilder()
        rb.query("page", "1")
        rb.query("size", "10")
        assertEquals(2, rb.queryParams.size)
    }
    @Test fun `headers`() {
        val rb = RequestBuilder()
        rb.header("X-Custom", "value")
        assertEquals("value", rb.headers["X-Custom"])
    }
    @Test fun `form body`() {
        val rb = RequestBuilder()
        rb.form("user" to "alice", "pass" to "secret")
        assertTrue(rb.bodyContent!!.contains("user=alice"))
        assertEquals("application/x-www-form-urlencoded", rb.contentType)
    }
}
