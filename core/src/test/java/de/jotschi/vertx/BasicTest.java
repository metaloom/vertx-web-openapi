package de.jotschi.vertx;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.vertx.core.http.HttpMethod.POST;

import de.jotschi.vertx.route.ApiRoute;
import de.jotschi.vertx.route.request.impl.RequestImpl;
import de.jotschi.vertx.route.response.impl.ResponseImpl;
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
		test.exampleRequest("text/plain", new RequestImpl().description("Test request").body("The example request"));
		test.exampleResponse(OK, new ResponseImpl().mimeType("text/plain").description("Test response example").body("The test response."));
		// test.events(PLUGIN_DEPLOYED, PLUGIN_DEPLOYING);
		test.handler(rc -> {
			rc.response().end("Done");
		});
	}
}
