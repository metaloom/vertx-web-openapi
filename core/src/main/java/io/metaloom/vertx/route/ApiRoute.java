package io.metaloom.vertx.route;

import java.util.Map;
import java.util.Set;

import io.metaloom.vertx.event.Event;
import io.metaloom.vertx.route.impl.APIRouteImpl;
import io.metaloom.vertx.route.query.QueryParameter;
import io.metaloom.vertx.route.request.Request;
import io.metaloom.vertx.route.response.Response;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.RoutingContext;

/**
 * A endpoint is a wrapper for a Vert.x Route. It additionally contains descriptive information to be used for documentation purposes.
 */
public interface ApiRoute extends Route {

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
	ApiRoute handler(Handler<RoutingContext> handler);

	/**
	 * Set the path for the endpoint.
	 * 
	 * @param path
	 */
	ApiRoute path(String path);

	/**
	 * Method for the endpoint.
	 * 
	 * @param method
	 */
	ApiRoute method(HttpMethod method);

	/**
	 * Description of the endpoint.
	 * 
	 * @param description
	 */
	ApiRoute description(String description);

	String description();

	ApiRoute produces(String contentType);

	ApiRoute traits(String... traits);

	String[] traits();

	ApiRoute events(Event... events);

	Set<Event> events();

	HttpMethod method();

	String pathRegex();

	ApiRoute displayName(String name);

	String path();

	ApiRoute useNormalisedPath(boolean useNormalisedPath);

	ApiRoute enable();

	ApiRoute disable();

	ApiRoute remove();

	ApiRoute failureHandler(Handler<RoutingContext> failureHandler);

	ApiRoute blockingHandler(Handler<RoutingContext> requestHandler, boolean ordered);

	ApiRoute blockingHandler(Handler<RoutingContext> requestHandler);

	/**
	 * Validate that all required fields have been set.
	 * 
	 * @return
	 */
	ApiRoute validate();

	ApiRoute last();

	ApiRoute order(int order);

	ApiRoute consumes(String contentType);

	ApiRoute pathRegex(String path);

	// Example - Request

	ApiRoute exampleRequest(String mimeType, Request request);

	Map<Integer, Response> exampleResponses();

	// Example - Response

	ApiRoute exampleResponse(HttpResponseStatus status, Response response);

	Map<String, Request> exampleRequests();

	// Example - Query Parameters

	ApiRoute queryParameter(String key, String description, String example);

	Map<String, QueryParameter> queryParameters();

	static ApiRoute create(Route route) {
		return new APIRouteImpl(route);
	}

}
