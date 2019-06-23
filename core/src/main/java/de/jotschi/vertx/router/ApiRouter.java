package de.jotschi.vertx.router;

import java.util.List;

import de.jotschi.vertx.route.ApiRoute;
import de.jotschi.vertx.router.impl.RouterEntry;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public interface ApiRouter extends Router {

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
	ApiRouter label(String label);

	/**
	 * Description of the resource.
	 * 
	 * @return
	 */
	String description();

	ApiRouter description(String description);

	/**
	 * @see Router#route()
	 */
	ApiRoute route();

	/**
	 * @see Router#route(HttpMethod, String)
	 */
	ApiRoute route(HttpMethod method, String path);

	/**
	 * Return the wrapped router.
	 * 
	 * @return
	 */
	Router getDelegate();

	List<ApiRoute> getEndpointRoutes();

	List<RouterEntry> getSubRouters();

	ApiRoute route(String path);
}
