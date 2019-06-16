package de.jotschi.vertx;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.vertx.core.http.HttpMethod.POST;

import de.jotschi.vertx.endpoint.EndpointRoute;
import de.jotschi.vertx.endpoint.resource.ResourceRouter;

public class TestAPI {

	private ResourceRouter router;

	public TestAPI(ResourceRouter router) {
		this.router = router;
	}

	public void routes() {
		EndpointRoute test = router.route();
		test.path("/test");
		test.method(POST);
		test.description("Test endpoint");
		test.produces("application/json");
		test.exampleRequest("Test request");
		test.exampleResponse(OK, "Test response example", "The test response.");
		// test.events(PLUGIN_DEPLOYED, PLUGIN_DEPLOYING);
		test.handler(rc -> {
			rc.response().end("Done");
		});
	}
}
