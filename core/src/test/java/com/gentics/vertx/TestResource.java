package com.gentics.vertx;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.vertx.core.http.HttpMethod.POST;

import de.jotschi.vertx.endpoint.Endpoint;
import de.jotschi.vertx.resource.impl.AbstractResource;

public class TestResource extends AbstractResource {

	@Override
	public String label() {
		return "Test Resource";
	}

	@Override
	public String description() {
		return "Resource used for testing";
	}
	
	public void routes() {
		Endpoint test = createEndpoint();
		test.path("/test");
		test.method(POST);
		test.description("Test endpoint");
		test.produces("application/json");
		test.exampleRequest("Test request");
		test.exampleResponse(OK, "Test response example", "The test response.");
		//test.events(PLUGIN_DEPLOYED, PLUGIN_DEPLOYING);
		test.handler(rc -> {
			rc.response().end("Done");
		});
	}

}
