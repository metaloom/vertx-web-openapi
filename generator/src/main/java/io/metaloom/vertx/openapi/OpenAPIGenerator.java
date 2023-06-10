package io.metaloom.vertx.openapi;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.metaloom.vertx.route.ApiRoute;
import io.metaloom.vertx.route.query.QueryParameter;
import io.metaloom.vertx.router.ApiRouter;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.servers.Server;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;

/**
 * OpenAPI generator which consumes {@link ApiRouter} to extract route information for the generated Open API 3 spec.
 */
public class OpenAPIGenerator {

	public static final Logger log = LoggerFactory.getLogger(OpenAPIGenerator.class);

	protected String baseUrl;

	protected String title;

	protected ApiRouter router;

	protected String description;

	protected String version;

	private OpenAPIGenerator(String baseUrl, String description, ApiRouter router, String title, String version) {
		this.baseUrl = baseUrl;
		this.description = description;
		this.router = router;
		this.title = title;
		this.version = version;
	}

	public String generate() throws JsonProcessingException {
		OpenAPI spec = new OpenAPI();

		Info info = new Info();
		info.setDescription(description);
		info.setTitle(title);
		info.version(version);
		spec.setInfo(info);

		Server server = new Server();
		server.setDescription(description);
		server.setUrl(baseUrl);
		spec.addServersItem(server);

		addRoutes(spec, "", router);

		router.getSubRouters().forEach(entry -> {
			String basePath = entry.getPath();
			ApiRouter currentRouter = entry.getRouter();
			walkRouter(spec, basePath, currentRouter);
		});

		// Tag tag = new Tag();
		// tag.setDescription("tag");
		// spec.addTagsItem(tag);
		return Yaml.pretty().writeValueAsString(spec);
	}

	private static void addRoutes(OpenAPI spec, String basePath, ApiRouter router) {
		router.getApiRoutes().forEach(r -> {
			String path = basePath + r.path();
			PathItem pathItem = spec.getPaths() == null ? null : spec.getPaths().get(path);
			if (pathItem == null) {
				pathItem = new PathItem();
				pathItem.setDescription(r.description());
			}
			log.info("Adding route {" + r.method() + " " + path + "}");
			updateItem(pathItem, r);
			spec.path(path, pathItem);
		});
	}

	private static void walkRouter(OpenAPI spec, String basePath, ApiRouter router) {
		router.getSubRouters().forEach(entry -> {
			walkRouter(spec, basePath + entry.getPath(), entry.getRouter());
		});

		addRoutes(spec, basePath, router);
	}

	private static PathItem updateItem(PathItem pathItem, ApiRoute r) {
		HttpMethod method = r.method();
		if (method != null) {
			Operation op = new Operation();

			op.setDescription(r.description());
			addParameters(r, op);
			// op.summary("The summary");

			addResponses(r, op);
			addRequests(r, op);

			switch (r.method().name().toUpperCase()) {
			case "HEAD":
				pathItem.setHead(op);
				break;
			case "DELETE":
				pathItem.setDelete(op);
				break;
			case "TRACE":
				pathItem.setTrace(op);
				break;
			case "PATCH":
				pathItem.setPatch(op);
				break;
			case "PUT":
				pathItem.setPut(op);
				break;
			case "GET":
				pathItem.setGet(op);
				break;
			case "POST":
				pathItem.setPost(op);
				break;
			case "OPTIONS":
			case "CONNECT":
			default:
				// Not supported
			}

		}
		return pathItem;
	}

	private static void addParameters(ApiRoute r, Operation op) {
		Map<String, QueryParameter> params = r.queryParameters();
		for (Entry<String, QueryParameter> entry : params.entrySet()) {
			String key = entry.getKey();
			QueryParameter queryParam = entry.getValue();
			Object value = queryParam.example();

			Parameter parameter = new Parameter();
			parameter.setName(key);
			parameter.description(queryParam.description());
			parameter.example(value);
			parameter.schema(schemaOf(value));
			// parameter.required( )
			op.addParametersItem(parameter);
		}
	}

	private static void addRequests(ApiRoute r, Operation op) {
		if (r.exampleRequests().isEmpty()) {
			return;
		}
		r.exampleRequests().forEach((exampleType, exampleRequest) -> {
			if (exampleRequest.body() != null) {
				RequestBody body = new RequestBody();
				Content content = new Content();
				MediaType mediaType = new MediaType();
				mediaType.example(exampleRequest.body());
				content.put(exampleType, mediaType);
				body.setContent(content);
				op.requestBody(body);
			}

			Map<String, Example> headerExamples = new HashMap<>();
			exampleRequest.headers().forEach(header -> {
				String name = header.name();
				Object value = header.example();

				Example headerValue = new Example();
				headerValue.setValue(value);
				headerExamples.put(name, headerValue);
				Parameter headers = new Parameter().in("header");
				headers.setName(name);
				headers.description(header.description());
				headers.setExamples(headerExamples);

				headers.schema(schemaOf(value));
				op.addParametersItem(headers);
			});
		});
	}

	private static Schema<?> schemaOf(Object value) {
		Schema<?> schema = new Schema<>();
		if (value instanceof Number) {
			schema.type("number");
		}
		if (value instanceof String) {
			schema.type("string");
		}
		if (value instanceof Boolean) {
			schema.type("boolean");
		}
		return schema;
	}

	private static void addResponses(ApiRoute r, Operation op) {
		ApiResponses responses = new ApiResponses();
		if (r.exampleResponses().isEmpty()) {
			// ApiResponse apiResponse = new ApiResponse();
			// apiResponse.setDescription("tadsgsd");
			// responses.addApiResponse("204", apiResponse);
			return;
		}
		r.exampleResponses().forEach((code, response) -> {
			ApiResponse apiResponse = new ApiResponse();
			apiResponse.setDescription(response.description());

			if (response.body() != null) {
				Content content = new Content();
				MediaType mediaType = new MediaType();
				Object example = response.body();
				if (example instanceof JsonObject json) {
					example = json.encodePrettily();
				}
				mediaType.setExample(example);
				content.put(response.mimeType(), mediaType);
				apiResponse.setContent(content);
			}
			response.headers().entrySet().forEach(header -> {
				apiResponse.addHeaderObject(header.getKey(), new Header().example(header.getValue()));
			});
			responses.addApiResponse(String.valueOf(code), apiResponse);
		});
		op.setResponses(responses);
	}

	public static class Builder {

		private String baseUrl = "http://localhost:8080";
		private String description;
		private ApiRouter apiRouter;
		private String title;
		private String version;

		/**
		 * Set the API baseUrl.
		 * 
		 * @param baseUrl
		 * @return
		 */
		public Builder baseUrl(String baseUrl) {
			this.baseUrl = baseUrl;
			return this;
		}

		/**
		 * Set the API description.
		 * 
		 * @param description
		 * @return
		 */
		public Builder description(String description) {
			this.description = description;
			return this;
		}

		/**
		 * Set the API version.
		 * 
		 * @param version
		 * @return
		 */
		public Builder version(String version) {
			this.version = version;
			return this;
		}

		/**
		 * Set the API router which should be used as a source for the endpoints.
		 * 
		 * @param apiRouter
		 * @return
		 */
		public Builder apiRouter(ApiRouter apiRouter) {
			this.apiRouter = apiRouter;
			return this;
		}

		/**
		 * Set the title for the API.
		 * 
		 * @param title
		 * @return
		 */
		public Builder title(String title) {
			this.title = title;
			return this;
		}

		/**
		 * Generate the API specification.
		 * 
		 * @return
		 * @throws JsonProcessingException
		 */
		public String generate() throws JsonProcessingException {
			Objects.requireNonNull(apiRouter, "An API Router has to be specified.");
			Objects.requireNonNull(baseUrl, "A valid baseurl has to be specified.");
			Objects.requireNonNull(title, "A valid title has to be specified.");
			Objects.requireNonNull(version, "A valid version has to be specified.");
			return new OpenAPIGenerator(baseUrl, description, apiRouter, title, version).generate();
		}
	}

	public static Builder builder() {
		return new Builder();
	}
}
