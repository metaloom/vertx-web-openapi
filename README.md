# Vert.x Web API

This project aims to provide wrappers for Vert.x Routes and Routers. These wrappers enhance the existing Vert.x API to provide extra information which can be used to generate documentation and specification of the defined API.

## Usage Example



```
ApiRouter root = new ApiRouterImpl(Vertx.vertx());
root.description("The root router");
root.route("/root1").method(HttpMethod.POST)
	.exampleRequest("application/json", "{ \"value\": \"The example request\"}", "The required request")
	.exampleResponse(OK, "text/plain", "The example response", "Regular response of this endpoint")
	.exampleResponse(BAD_REQUEST, "application/json", new JsonObject().put("test", "The example response"),
		"Regular response of this endpoint")
	.consumes("application/json")
	.produces("application/json");

ApiRouter level1 = new ApiRouterImpl(Vertx.vertx());
level1.route("/anotherRoute").method(HttpMethod.POST).consumes("application/json");

root.mountSubRouter("/test", level1);

String yaml = OpenAPIGenerator.gen(root);
```

## Core

Wrapper for Vert.x Routes/Router.

## Open API

TBD