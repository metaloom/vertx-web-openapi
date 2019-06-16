package de.jotschi.vertx;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.jotschi.vertx.endpoint.resource.impl.ResourceRouterImpl;
import io.vertx.core.Vertx;

public class ResourceRouterTest {

	@Test
	public void testRouting() {
		ResourceRouterImpl root = new ResourceRouterImpl(Vertx.vertx());
		root.description("The root router");

		ResourceRouterImpl level1 = new ResourceRouterImpl(Vertx.vertx());
		level1.route("/blub");

		root.mountSubRouter("/test", level1);
		assertEquals("The root router", root.description());
	}
}
