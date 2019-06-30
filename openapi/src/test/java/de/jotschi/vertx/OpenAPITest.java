package de.jotschi.vertx;

import static de.jotschi.vertx.route.request.impl.RequestImpl.request;
import static de.jotschi.vertx.route.response.impl.ResponseImpl.response;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.CREATED;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.jotschi.vertx.openapi.OpenAPIGenerator;
import de.jotschi.vertx.router.ApiRouter;
import de.jotschi.vertx.router.impl.ApiRouterImpl;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;

public class OpenAPITest {
	@Test
	public void testRouting() throws JsonProcessingException {
		ApiRouter api = createAPI();
		assertEquals("The root router", api.description());

		String yaml = OpenAPIGenerator.gen(api);
		System.out.println(yaml);
	}

	private ApiRouter createAPI() {
		ApiRouter root = new ApiRouterImpl(Vertx.vertx());
		root.description("The root router");
		root.route("/root1").method(HttpMethod.POST)
			.exampleRequest("application/json",
				request()
					.body("{ \"value\": \"The example request\"}")
					.description("The required request")
					.header("CUSTOM_HEADER", "ABC"))

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

		ApiRouter level1 = new ApiRouterImpl(Vertx.vertx());
		level1.route("/anotherRoute").method(HttpMethod.POST).consumes("application/json");

		ApiRouter level2 = new ApiRouterImpl(Vertx.vertx());
		level2.route("/onLevel3")
			.description("Route on level 3")
			.method(HttpMethod.POST)
			.queryParameter("query", "The query parameter", "test")
			.consumes("application/json");

		root.mountSubRouter("/test", level1);
		level1.mountSubRouter("/level2", level2);
		return root;
	}
}
