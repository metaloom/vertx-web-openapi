package de.jotschi.vertx.resource.impl;

import java.util.ArrayList;
import java.util.List;

import de.jotschi.vertx.endpoint.Endpoint;
import de.jotschi.vertx.endpoint.impl.EndpointImpl;
import de.jotschi.vertx.endpoint.resource.Resource;
import io.vertx.ext.web.Router;

public abstract class AbstractResource implements Resource {

	protected List<Endpoint> endpoints = new ArrayList<>();

	protected Resource parent;

	protected Router router = null;

	@Override
	public Endpoint createEndpoint() {
		Endpoint endpoint = new EndpointImpl(getRouter().route());
		endpoints.add(endpoint);
		return endpoint;
	}

	@Override
	public Router getRouter() {
		return router;
	}
}
