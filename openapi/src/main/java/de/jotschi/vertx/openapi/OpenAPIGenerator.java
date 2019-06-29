package de.jotschi.vertx.openapi;

import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.jotschi.vertx.route.ApiRoute;
import de.jotschi.vertx.route.query.QueryParameter;
import de.jotschi.vertx.router.ApiRouter;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class OpenAPIGenerator {

	public static final Logger log = LoggerFactory.getLogger(OpenAPIGenerator.class);

	public static String gen(ApiRouter router) throws JsonProcessingException {
		OpenAPI spec = new OpenAPI();

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
		router.getEndpointRoutes().forEach(r -> {
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
		for(Entry<String, QueryParameter> entry: params.entrySet()) {
			String key = entry.getKey();
			QueryParameter value = entry.getValue();
			
			Parameter parameter = new Parameter();
			parameter.setName(key);
			parameter.description(value.description());
			parameter.example(value.example());
			//parameter.required( )
			op.addParametersItem(parameter);
		}
		
	}

	private static void addRequests(ApiRoute r, Operation op) {
		if (r.exampleRequests().isEmpty()) {
			return;
		}
		r.exampleRequests().forEach((exampleType, exampleRequest) -> {
			RequestBody body = new RequestBody();
			Content content = new Content();
			MediaType mediaType = new MediaType();
			mediaType.example(exampleRequest.body());
			content.put(exampleType, mediaType);

			body.setContent(content);
			op.requestBody(body);
		});

	}

	private static void addResponses(ApiRoute r, Operation op) {
		if (r.exampleResponses().isEmpty()) {
			return;
		}
		ApiResponses responses = new ApiResponses();
		r.exampleResponses().forEach((code, response) -> {
			ApiResponse apiResponse = new ApiResponse();
			apiResponse.setDescription(response.description());

			Content content = new Content();
			MediaType mediaType = new MediaType();
			Object example = response.example();
			if (example instanceof JsonObject) {
				example = ((JsonObject) example).encodePrettily();
			}
			mediaType.setExample(example);

			content.put(response.mimeType(), mediaType);
			apiResponse.setContent(content);
			responses.addApiResponse(String.valueOf(code), apiResponse);
		});
		op.setResponses(responses);
	}

}
