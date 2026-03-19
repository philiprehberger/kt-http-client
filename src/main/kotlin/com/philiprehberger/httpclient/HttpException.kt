package com.philiprehberger.httpclient

/** Exception for non-2xx HTTP responses. */
public class HttpException(public val status: Int, public val body: String) : RuntimeException("HTTP $status")
