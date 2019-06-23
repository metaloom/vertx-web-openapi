package de.jotschi.vertx;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.vertx.core.http.HttpMethod.POST;

import de.jotschi.vertx.route.ApiRoute;
import de.jotschi.vertx.router.ApiRouter;

public class BasicTest {

	private ApiRouter router;

	public BasicTest(ApiRouter router) {
		this.router = router;
	}

	public void routes() {
		ApiRoute test = router.route();
		test.path("/test");
		test.method(POST);
		test.description("Test endpoint");
		test.produces("application/json");
		test.exampleRequest("text/plain", "Test request", "The example request");
		test.exampleResponse(OK, "text/plain", "Test response example", "The test response.");
		// test.events(PLUGIN_DEPLOYED, PLUGIN_DEPLOYING);
		test.handler(rc -> {
			rc.response().end("Done");
		});
	}
}
