# Vert.x OpenAPI

This project provides wrappers for Vert.x Routes and Routers. These wrappers enhance the existing Vert.x API to provide extra information which can be used to generate documentation and specification of the defined API.

## Maven

```
<dependency>
    <groupId>io.metaloom.vertx</groupId>
    <artifactId>vertx-web-openapi-generator</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## Usage Example

```java
Vertx vertx = Vertx.vertx();

// 1. Use ApiRouter instead of Router
ApiRouter api = ApiRouter.create(vertx);
api.description("The root router");
api.route("/root1").method(HttpMethod.POST)
  .exampleRequest("application/json",
    request()
      .body("{ \"value\": \"The example request\"}")
      .description("The required request")
      .header("CUSTOM_HEADER", "Example header", "ABC")
      .header("CUSTOM_HEADER2", "Example header2", "ABC2"))

  .exampleResponse(OK,
    response("text/plain")
      .body("The example response")
      .description("Regular response of this endpoint"))

  .exampleResponse(BAD_REQUEST,
    response("application/json")
      .body(new JsonObject().put("test", "The example response"))
      .description("Regular response of this endpoint"))

  .exampleResponse(CREATED, response()
    .description("Element created")
    .header("API_VERSION", "1.0"))
  .consumes("application/json")
  .produces("application/json");

ApiRouter level1 = ApiRouter.create(vertx);
level1.route("/anotherRoute")
  .method(HttpMethod.POST)
  .consumes("application/json");

ApiRouter level2 = ApiRouter.create(vertx);
level2.route("/onLevel3")
  .description("Route on level 3")
  .method(HttpMethod.POST)
  .queryParameter("query", "The query parameter", "test")
  .consumes("application/json");

api.mountSubRouter("/test", level1);
level1.mountSubRouter("/level2", level2);
assertEquals("The root router", api.description());

// Now generate the OpenAPI spec using the defined routes
Builder builder = OpenAPIGenerator.builder();
builder.baseUrl("https://server.tld");
builder.description("The API for our example server");
builder.apiRouter(api);

String yaml = builder.generate();
System.out.println(yaml);

```

## Limitations / WIP

This library is still work in progress and not every detail of the OpenAPI spec can be defined and generated using the ApiRoute/ApiRouter wrappers.

Still missing:

* Response / Request schema support.
* Full query parameter type support.


