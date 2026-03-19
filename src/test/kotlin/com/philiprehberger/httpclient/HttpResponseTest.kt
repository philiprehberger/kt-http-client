package com.philiprehberger.httpclient

import kotlin.test.*

class HttpResponseTest {
    @Test fun `isSuccess`() {
        assertTrue(HttpResponse(200, "", emptyMap()).isSuccess())
        assertTrue(HttpResponse(201, "", emptyMap()).isSuccess())
        assertFalse(HttpResponse(404, "", emptyMap()).isSuccess())
        assertFalse(HttpResponse(500, "", emptyMap()).isSuccess())
    }
}
