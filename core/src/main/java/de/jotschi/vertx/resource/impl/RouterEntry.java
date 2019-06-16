package de.jotschi.vertx.resource.impl;

import de.jotschi.vertx.endpoint.resource.ResourceRouter;

public class RouterEntry {

	private final String path;
	private final ResourceRouter router;

	public RouterEntry(String path, ResourceRouter router) {
		this.path = path;
		this.router = router;
	}

	public String getPath() {
		return path;
	}

	public ResourceRouter getRouter() {
		return router;
	}
}