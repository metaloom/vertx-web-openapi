package de.jotschi.vertx.endpoint.resource;

import java.util.List;

import de.jotschi.vertx.endpoint.EndpointRoute;
import de.jotschi.vertx.resource.impl.RouterEntry;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

public interface ResourceRouter extends Router {

	/**
	 * Human readable label for the resource.
	 * 
	 * @return
	 */
	String label();

	/**
	 * Set the label for the router.
	 * 
	 * @param label
	 * @return
	 */
	ResourceRouter label(String label);

	/**
	 * Description of the resource.
	 * 
	 * @return
	 */
	String description();

	ResourceRouter description(String description);

	/**
	 * @see Router#route()
	 */
	EndpointRoute route();

	/**
	 * @see Router#route(HttpMethod, String)
	 */
	EndpointRoute route(HttpMethod method, String path);

	/**
	 * Return the wrapped router.
	 * 
	 * @return
	 */
	Router getDelegate();

	List<EndpointRoute> getEndpointRoutes();

	List<RouterEntry> getSubRouters();

}
