package de.jotschi.vertx.openapi;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.jotschi.vertx.endpoint.EndpointRoute;
import de.jotschi.vertx.endpoint.resource.ResourceRouter;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.tags.Tag;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class OpenAPIGenerator {

	public static final Logger log = LoggerFactory.getLogger(OpenAPIGenerator.class);

	public static String gen(ResourceRouter api) throws JsonProcessingException {
		OpenAPI spec = new OpenAPI();

		api.getEndpointRoutes().forEach(r -> {
			PathItem item = toItem(r);
			String path = r.path();
			log.info("Adding route {" + path + "}");
			spec.path(path, item);
		});

		api.getSubRouters().forEach(entry -> {
			String basePath = entry.getPath();
			ResourceRouter router = entry.getRouter();

			router.getEndpointRoutes().forEach(r -> {
				PathItem item = toItem(r);
				String path = basePath + r.path();
				log.info("Adding route of subrouter {" + path + "}");
				spec.path(path, item);
			});
		});

		Tag tag = new Tag();
		tag.setDescription("blub");
		spec.addTagsItem(tag);

		return Yaml.pretty().writeValueAsString(spec);
	}

	private static PathItem toItem(EndpointRoute r) {
		PathItem pathItem = new PathItem();

		pathItem.setDescription(r.description());

		HttpMethod method = r.method();
		if (method != null) {
			Operation op = new Operation();
			op.setDescription("blub");
			op.summary("The summary");
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

}
