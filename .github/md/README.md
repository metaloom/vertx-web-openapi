# Vert.x OpenAPI

This project provides wrappers for Vert.x Routes and Routers. These wrappers enhance the existing Vert.x API to provide extra information which can be used to generate documentation and specification of the defined API.

## Maven

```
<dependency>
    <groupId>io.metaloom.vertx</groupId>
    <artifactId>vertx-web-openapi-generator</artifactId>
    <version>${project.version}</version>
</dependency>
```

## Usage Example

```
```java
%{snippet|id=basicUsage|file=generator/src/test/java/io/metaloom/vertx/OpenAPITest.java}
```

## Limitations / WIP

This library is still work in progress and not every detail of the OpenAPI spec can be defined and generated using the ApiRoute/ApiRouter wrappers.

Still missing:

* Response / Request schema support.
* Full query parameter type support.


