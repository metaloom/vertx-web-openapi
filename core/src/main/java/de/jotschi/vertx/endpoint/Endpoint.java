package de.jotschi.vertx.endpoint;

import java.util.Map;
import java.util.Set;

import javax.activation.MimeType;

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
public interface Endpoint {

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
	Endpoint handler(Handler<RoutingContext> handler);

	/**
	 * Set the path for the endpoint.
	 * 
	 * @param path
	 */
	Endpoint path(String path);

	/**
	 * Method for the endpoint.
	 * 
	 * @param method
	 */
	Endpoint method(HttpMethod method);

	/**
	 * Description of the endpoint.
	 * 
	 * @param description
	 */
	Endpoint description(String description);

	String description();

	Endpoint produces(String contentType);

	Endpoint exampleRequest(String example);

	Endpoint exampleResponse(HttpResponseStatus code, Object example, String description);

	Endpoint traits(String... traits);

	String[] traits();

	Endpoint events(Event... events);

	Set<Event> events();

	HttpMethod method();

	String pathRegex();

	Endpoint displayName(String name);

	String path();

	Endpoint useNormalisedPath(boolean useNormalisedPath);

	Endpoint enable();

	Endpoint disable();

	Endpoint remove();

	Endpoint failureHandler(Handler<RoutingContext> failureHandler);

	Endpoint blockingHandler(Handler<RoutingContext> requestHandler, boolean ordered);

	Endpoint blockingHandler(Handler<RoutingContext> requestHandler);

	/**
	 * Validate that all required fields have been set.
	 * 
	 * @return
	 */
	Endpoint validate();

	Endpoint last();

	Endpoint order(int order);

	Endpoint consumes(String contentType);

	Endpoint pathRegex(String path);

	Map<Integer, Object> exampleResponses();

	Map<String, MimeType> exampleRequestMap();

	Map<String, QueryParameter> queryParameters();

	Endpoint queryParameter(String key, String description, String example);

	Endpoint exampleResponse(HttpResponseStatus status, String description);

	Endpoint exampleResponse(HttpResponseStatus status, String description, String headerName, String example, String headerDescription);

	/**
	 * Set the example request model for the endpoint.
	 * 
	 * @param model
	 * @return
	 */
	Endpoint exampleRequest(Object model);

}
