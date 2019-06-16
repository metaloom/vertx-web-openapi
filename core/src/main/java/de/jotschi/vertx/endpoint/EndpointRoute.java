package de.jotschi.vertx.endpoint;

import java.util.Map;
import java.util.Set;

import javax.activation.MimeType;

import de.jotschi.vertx.endpoint.impl.EndpointRouteImpl;
import de.jotschi.vertx.endpoint.query.QueryParameter;
import de.jotschi.vertx.event.Event;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.RoutingContext;

/**
 * A endpoint is a wrapper for a Vert.x Route. It additionally contains descriptive information to be used for documentation purposes.
 */
public interface EndpointRoute extends Route {

	/**
	 * Return the vert.x route for the endpoint.
	 * 
	 * @return
	 */
	Route getRoute();

	/**
	 * Return the display name of the endpoint.
	 * 
	 * @return
	 */
	String displayName();

	/**
	 * Set the handler for the endpoint.
	 * 
	 * @param handler
	 * @return
	 */
	EndpointRoute handler(Handler<RoutingContext> handler);

	/**
	 * Set the path for the endpoint.
	 * 
	 * @param path
	 */
	EndpointRoute path(String path);

	/**
	 * Method for the endpoint.
	 * 
	 * @param method
	 */
	EndpointRoute method(HttpMethod method);

	/**
	 * Description of the endpoint.
	 * 
	 * @param description
	 */
	EndpointRoute description(String description);

	String description();

	EndpointRoute produces(String contentType);

	EndpointRoute exampleRequest(String example);

	EndpointRoute exampleResponse(HttpResponseStatus code, Object example, String description);

	EndpointRoute traits(String... traits);

	String[] traits();

	EndpointRoute events(Event... events);

	Set<Event> events();

	HttpMethod method();

	String pathRegex();

	EndpointRoute displayName(String name);

	String path();

	EndpointRoute useNormalisedPath(boolean useNormalisedPath);

	EndpointRoute enable();

	EndpointRoute disable();

	EndpointRoute remove();

	EndpointRoute failureHandler(Handler<RoutingContext> failureHandler);

	EndpointRoute blockingHandler(Handler<RoutingContext> requestHandler, boolean ordered);

	EndpointRoute blockingHandler(Handler<RoutingContext> requestHandler);

	/**
	 * Validate that all required fields have been set.
	 * 
	 * @return
	 */
	EndpointRoute validate();

	EndpointRoute last();

	EndpointRoute order(int order);

	EndpointRoute consumes(String contentType);

	EndpointRoute pathRegex(String path);

	Map<Integer, Object> exampleResponses();

	Map<String, MimeType> exampleRequestMap();

	Map<String, QueryParameter> queryParameters();

	EndpointRoute queryParameter(String key, String description, String example);

	EndpointRoute exampleResponse(HttpResponseStatus status, String description);

	EndpointRoute exampleResponse(HttpResponseStatus status, String description, String headerName, String example, String headerDescription);

	/**
	 * Set the example request model for the endpoint.
	 * 
	 * @param model
	 * @return
	 */
	EndpointRoute exampleRequest(Object model);

	static EndpointRoute create(Route route) {
		return new EndpointRouteImpl(route);
	}

}
