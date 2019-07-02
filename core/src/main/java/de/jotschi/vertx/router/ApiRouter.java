package de.jotschi.vertx.router;

import java.util.List;

import de.jotschi.vertx.route.ApiRoute;
import de.jotschi.vertx.router.impl.ApiRouterImpl;
import de.jotschi.vertx.router.impl.RouterEntry;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

/**
 * The API Router is a {@link Router} which contains additional methods which can be used to describe the router.
 */
public interface ApiRouter extends Router {

	/**
	 * Create a new router.
	 * 
	 * @param vertx
	 * @return
	 */
	static ApiRouter create(Vertx vertx) {
		return new ApiRouterImpl(vertx);
	}

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

	/**
	 * Set the description.
	 * 
	 * @param description
	 * @return
	 */
	ApiRouter description(String description);

	/**
	 * Return list of API routes which have been added to the router.
	 * 
	 * @return
	 */
	List<ApiRoute> getApiRoutes();

	/**
	 * Return a list of subrouters of the router.
	 * 
	 * @return
	 */
	List<RouterEntry> getSubRouters();

	/**
	 * @see Router#route()
	 */
	ApiRoute route();

	/**
	 * @see Router#route(HttpMethod, String)
	 */
	ApiRoute route(HttpMethod method, String path);

	/**
	 * @see Router#route(String)
	 */
	ApiRoute route(String path);

	/**
	 * Return the wrapped router.
	 * 
	 * @return
	 */
	Router getDelegate();
}
