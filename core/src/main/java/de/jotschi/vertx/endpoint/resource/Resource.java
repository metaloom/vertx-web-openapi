package de.jotschi.vertx.endpoint.resource;

import de.jotschi.vertx.endpoint.Endpoint;
import io.vertx.ext.web.Router;

public interface Resource {

	/**
	 * Human readable label for the resource.
	 * 
	 * @return
	 */
	String label();

	/**
	 * Description of the resource.
	 * 
	 * @return
	 */
	String description();

	/**
	 * Create a new endpoint.
	 * 
	 * @return
	 */
	Endpoint createEndpoint();

	Router getRouter();
}
