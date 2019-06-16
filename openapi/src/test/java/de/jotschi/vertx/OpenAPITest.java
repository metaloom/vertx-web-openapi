package de.jotschi.vertx;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import de.jotschi.vertx.endpoint.resource.ResourceRouter;
import de.jotschi.vertx.endpoint.resource.impl.ResourceRouterImpl;
import de.jotschi.vertx.openapi.OpenAPIGenerator;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;

public class OpenAPITest {
	@Test
	public void testRouting() throws JsonProcessingException {
		ResourceRouter api = createAPI();
		assertEquals("The root router", api.description());

		String yaml = OpenAPIGenerator.gen(api);
		System.out.println(yaml);
	}

	private ResourceRouter createAPI() {
		ResourceRouter root = new ResourceRouterImpl(Vertx.vertx());
		root.description("The root router");
		root.route("/root1").method(HttpMethod.POST).consumes("application/json").produces("application/json");

		ResourceRouter level1 = new ResourceRouterImpl(Vertx.vertx());
		level1.route("/blub").method(HttpMethod.POST).consumes("application/json");

		root.mountSubRouter("/test", level1);
		return root;
	}
}
