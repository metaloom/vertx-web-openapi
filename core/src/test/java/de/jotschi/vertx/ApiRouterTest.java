package de.jotschi.vertx;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.jotschi.vertx.router.impl.ApiRouterImpl;
import io.vertx.core.Vertx;

public class ApiRouterTest {

	@Test
	public void testRouting() {
		ApiRouterImpl root = new ApiRouterImpl(Vertx.vertx());
		root.description("The root router");

		ApiRouterImpl level1 = new ApiRouterImpl(Vertx.vertx());
		level1.route("/blub");

		root.mountSubRouter("/test", level1);
		assertEquals("The root router", root.description());
	}
}
