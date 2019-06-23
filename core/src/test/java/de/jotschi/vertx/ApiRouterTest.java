package de.jotschi.vertx;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.jotschi.vertx.router.impl.APIRouterImpl;
import io.vertx.core.Vertx;

public class ApiRouterTest {

	@Test
	public void testRouting() {
		APIRouterImpl root = new APIRouterImpl(Vertx.vertx());
		root.description("The root router");

		APIRouterImpl level1 = new APIRouterImpl(Vertx.vertx());
		level1.route("/blub");

		root.mountSubRouter("/test", level1);
		assertEquals("The root router", root.description());
	}
}
