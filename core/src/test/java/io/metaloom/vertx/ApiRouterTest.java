package io.metaloom.vertx;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.metaloom.vertx.router.ApiRouter;
import io.vertx.core.Vertx;

public class ApiRouterTest {

	@Test
	public void testRouting() {
		Vertx vertx = Vertx.vertx();

		ApiRouter root = ApiRouter.create(vertx);
		root.description("The root router");

		ApiRouter level1 = ApiRouter.create(vertx);
		level1.route("/blub");

		root.mountSubRouter("/test", level1);
		assertEquals("The root router", root.description());
	}
}
