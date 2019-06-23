package de.jotschi.vertx.router.impl;

import de.jotschi.vertx.router.ApiRouter;

public class RouterEntry {

	private final String path;
	private final ApiRouter router;

	public RouterEntry(String path, ApiRouter router) {
		this.path = path;
		this.router = router;
	}

	public String getPath() {
		return path;
	}

	public ApiRouter getRouter() {
		return router;
	}
}