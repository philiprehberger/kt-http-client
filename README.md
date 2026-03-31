# http-client

[![Tests](https://github.com/philiprehberger/kt-http-client/actions/workflows/publish.yml/badge.svg)](https://github.com/philiprehberger/kt-http-client/actions/workflows/publish.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.philiprehberger/http-client.svg)](https://central.sonatype.com/artifact/com.philiprehberger/http-client)
[![Last updated](https://img.shields.io/github/last-commit/philiprehberger/kt-http-client)](https://github.com/philiprehberger/kt-http-client/commits/main)

Lightweight HTTP client DSL with retry, timeout, and JSON support.

## Installation

### Gradle (Kotlin DSL)

```kotlin
implementation("com.philiprehberger:http-client:0.1.6")
```

### Maven

```xml
<dependency>
    <groupId>com.philiprehberger</groupId>
    <artifactId>http-client</artifactId>
    <version>0.1.6</version>
</dependency>
```

## Usage

```kotlin
import com.philiprehberger.httpclient.*

val client = httpClient {
    baseUrl = "https://api.example.com"
    timeout = 10.seconds
    defaultHeaders["Authorization"] = "Bearer $token"
}

val users = client.get("/users") { query("page", "1") }
val created = client.post("/users") { json(CreateUserRequest("Alice")) }
```

## API

| Function / Class | Description |
|------------------|-------------|
| `httpClient { }` | Create a configured HTTP client |
| `HttpClient.get(path) { }` | GET request |
| `HttpClient.post(path) { json(body) }` | POST with JSON body |
| `RequestBuilder.query(key, value)` | Add query parameter |
| `RequestBuilder.header(name, value)` | Add header |
| `RequestBuilder.form(vararg pairs)` | Form-encoded body |
| `HttpResponse.json<T>()` | Deserialize response body |
| `HttpResponse.isSuccess()` | Check 2xx status |
| `HttpException` | Thrown for non-2xx responses |

## Development

```bash
./gradlew test
./gradlew build
```

## Support

If you find this project useful:

⭐ [Star the repo](https://github.com/philiprehberger/kt-http-client)

🐛 [Report issues](https://github.com/philiprehberger/kt-http-client/issues?q=is%3Aissue+is%3Aopen+label%3Abug)

💡 [Suggest features](https://github.com/philiprehberger/kt-http-client/issues?q=is%3Aissue+is%3Aopen+label%3Aenhancement)

❤️ [Sponsor development](https://github.com/sponsors/philiprehberger)

🌐 [All Open Source Projects](https://philiprehberger.com/open-source-packages)

💻 [GitHub Profile](https://github.com/philiprehberger)

🔗 [LinkedIn Profile](https://www.linkedin.com/in/philiprehberger)

## License

[MIT](LICENSE)
