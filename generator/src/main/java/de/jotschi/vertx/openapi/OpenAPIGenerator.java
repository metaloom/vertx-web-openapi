package de.jotschi.vertx.openapi;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.jotschi.vertx.route.ApiRoute;
import de.jotschi.vertx.route.query.QueryParameter;
import de.jotschi.vertx.router.ApiRouter;
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
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * OpenAPI generator which consumes {@link ApiRouter} to extract route information for the generated Open API 3 spec.
 */
public class OpenAPIGenerator {

	public static final Logger log = LoggerFactory.getLogger(OpenAPIGenerator.class);

	protected String baseUrl;

	protected String title;

	protected ApiRouter router;

	protected String description;

	private OpenAPIGenerator(String baseUrl, String description, ApiRouter router, String title) {
		this.baseUrl = baseUrl;
		this.description = description;
		this.router = router;
		this.title = title;
	}

	public String generate() throws JsonProcessingException {
		OpenAPI spec = new OpenAPI();

		Info info = new Info();
		info.setDescription(description);
		info.setTitle(title);
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
			PathItem item = toItem(r);
			String path = basePath + r.path();
			log.info("Adding route {" + path + "}");
			spec.path(path, item);
		});

	}

	private static void walkRouter(OpenAPI spec, String basePath, ApiRouter router) {
		router.getSubRouters().forEach(entry -> {
			walkRouter(spec, basePath + entry.getPath(), entry.getRouter());
		});

		addRoutes(spec, basePath, router);

	}

	private static PathItem toItem(ApiRoute r) {
		PathItem pathItem = new PathItem();

		pathItem.setDescription(r.description());

		HttpMethod method = r.method();
		if (method != null) {
			Operation op = new Operation();

			op.setDescription(r.description());
			addParameters(r, op);
			// op.summary("The summary");

			addResponses(r, op);
			addRequests(r, op);

			switch (r.method()) {
			case HEAD:
				pathItem.setHead(op);
				break;
			case DELETE:
				pathItem.setDelete(op);
				break;
			case TRACE:
				pathItem.setTrace(op);
				break;
			case PATCH:
				pathItem.setPatch(op);
				break;
			case OTHER:
				break;
			case PUT:
				pathItem.setPut(op);
				break;
			case GET:
				pathItem.setGet(op);
				break;
			case POST:
				pathItem.setPost(op);
				break;
			case OPTIONS:
			case CONNECT:
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
		if (r.exampleResponses().isEmpty()) {
			return;
		}
		ApiResponses responses = new ApiResponses();
		r.exampleResponses().forEach((code, response) -> {
			ApiResponse apiResponse = new ApiResponse();
			apiResponse.setDescription(response.description());

			if (response.body() != null) {
				Content content = new Content();
				MediaType mediaType = new MediaType();
				Object example = response.body();
				if (example instanceof JsonObject) {
					example = ((JsonObject) example).encodePrettily();
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
			return new OpenAPIGenerator(baseUrl, description, apiRouter, title).generate();
		}
	}

	public static Builder builder() {
		return new Builder();
	}
}
