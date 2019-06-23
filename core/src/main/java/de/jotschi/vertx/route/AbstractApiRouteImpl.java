package de.jotschi.vertx.route;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.jotschi.vertx.event.Event;
import de.jotschi.vertx.route.header.Header;
import de.jotschi.vertx.route.header.impl.HeaderImpl;
import de.jotschi.vertx.route.query.QueryParameter;
import de.jotschi.vertx.route.query.impl.QueryParameterImpl;
import de.jotschi.vertx.route.request.Request;
import de.jotschi.vertx.route.request.impl.RequestImpl;
import de.jotschi.vertx.route.response.Response;
import de.jotschi.vertx.route.response.impl.ResponseImpl;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public abstract class AbstractApiRouteImpl implements ApiRoute {

	protected static final Logger log = LoggerFactory.getLogger(ApiRoute.class);

	protected Route route;

	protected String displayName;

	protected String description;

	/**
	 * Uri Parameters which map to the used path segments
	 */
	protected Map<String, QueryParameter> queryParameters = new HashMap<>();

	/**
	 * Map of example responses for the corresponding status code.
	 */
	protected Map<Integer, Response> exampleResponses = new HashMap<>();

	protected Map<Integer, Class<?>> exampleResponseClasses = new HashMap<>();

	protected Set<Event> events = new HashSet<>();

	protected String[] traits = new String[] {};

	/**
	 * Mimetype, Example
	 */
	protected HashMap<String, Request> exampleRequests = new HashMap<>();

	protected String pathRegex;

	protected HttpMethod method;

	protected final Set<String> consumes = new LinkedHashSet<>();

	protected final Set<String> produces = new LinkedHashSet<>();

	protected Map<String, QueryParameter> parameters = new HashMap<>();

	public AbstractApiRouteImpl(Route route) {
		this.route = route;
	}

	@Override
	public Route getRoute() {
		return route;
	}

	@Override
	public ApiRoute handler(Handler<RoutingContext> handler) {
		validate();
		route.handler(handler);
		return this;
	}

	/**
	 * Create a new endpoint wrapper using the provided router to create the wrapped route instance.
	 * 
	 * @param router
	 */
	public AbstractApiRouteImpl(Router router) {
		this.route = router.route();
	}

	@Override
	public ApiRoute path(String path) {
		route.path(path);
		return this;
	}

	@Override
	public ApiRoute method(HttpMethod method) {
		if (this.method != null) {
			throw new RuntimeException(
				"The method for the endpoint was already set. The endpoint wrapper currently does not support more than one method per route.");
		}
		this.method = method;
		route.method(method);
		return this;
	}

	@Override
	public ApiRoute pathRegex(String path) {
		this.pathRegex = path;
		route.pathRegex(path);
		return this;
	}

	@Override
	public ApiRoute produces(String contentType) {
		produces.add(contentType);
		route.produces(contentType);
		return this;
	}

	@Override
	public ApiRoute consumes(String contentType) {
		consumes.add(contentType);
		route.consumes(contentType);
		return this;
	}

	@Override
	public ApiRoute order(int order) {
		route.order(order);
		return this;
	}

	@Override
	public ApiRoute last() {
		route.last();
		return this;
	}

	@Override
	public ApiRoute validate() {
		if (!produces.isEmpty() && exampleResponses.isEmpty()) {
			log.error("Endpoint {" + path() + "} has no example response.");
			throw new RuntimeException("Endpoint {" + path() + "} has no example responses.");
		}
		// if ((consumes.contains(APPLICATION_JSON) || consumes.contains(APPLICATION_JSON_UTF8)) && exampleRequestMap == null) {
		// log.error("Endpoint {" + path() + "} has no example request.");
		// throw new RuntimeException("Endpoint has no example request.");
		// }
		// if (isEmpty(description)) {
		// log.error("Endpoint {" + path() + "} has no description.");
		// throw new RuntimeException("No description was set");
		// }

		// Check whether all segments have a description.
		// List<String> segments = getNamedSegments();
		// for (String segment : segments) {
		// if (!queryParameters().containsKey(segment)) {
		// throw new RuntimeException("Missing URI description for path {" + path() + "} segment {" + segment + "}");
		// }
		// }
		return this;
	}

	// @Override
	// public List<String> getNamedSegments() {
	// List<String> allMatches = new ArrayList<String>();
	// Matcher m = Pattern.compile("\\{[^}]*\\}").matcher(path());
	// while (m.find()) {
	// allMatches.add(m.group().substring(1, m.group().length() - 1));
	// }
	// return allMatches;
	// }

	@Override
	public ApiRoute blockingHandler(Handler<RoutingContext> requestHandler) {
		route.blockingHandler(requestHandler);
		return this;
	}

	@Override
	public ApiRoute blockingHandler(Handler<RoutingContext> requestHandler, boolean ordered) {
		route.blockingHandler(requestHandler, ordered);
		return this;
	}

	@Override
	public ApiRoute failureHandler(Handler<RoutingContext> failureHandler) {
		route.failureHandler(failureHandler);
		return this;
	}

	@Override
	public ApiRoute remove() {
		route.remove();
		return this;
	}

	@Override
	public ApiRoute disable() {
		route.disable();
		return this;
	}

	@Override
	public ApiRoute enable() {
		route.enable();
		return this;
	}

	@Override
	public ApiRoute useNormalisedPath(boolean useNormalisedPath) {
		route.useNormalisedPath(useNormalisedPath);
		return this;
	}

	@Override
	public String path() {
		return route.getPath();
	}

	@Override
	public ApiRoute displayName(String name) {
		this.displayName = name;
		return this;
	}

	@Override
	public ApiRoute description(String description) {
		this.description = description;
		return this;
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public String displayName() {
		return displayName;
	}

	@Override
	public ApiRoute exampleResponse(HttpResponseStatus status, String description, String headerName, String example, String headerDescription) {
		Response response = new ResponseImpl();
		response.description(description);
		exampleResponses.put(status.code(), response);
		if (headerName != null) {
			Header header = new HeaderImpl();
			header.description(headerDescription);
			header.example(example);
			Map<String, Header> headers = new HashMap<>();
			headers.put(headerName, header);
			response.headers(headers);
		}
		return this;
	}

	@Override
	public ApiRoute exampleResponse(HttpResponseStatus status, String description) {
		return exampleResponse(status, description, null, null, null);
	}

	@Override
	public ApiRoute exampleResponse(HttpResponseStatus status, String mimeType, Object example, String description) {
		Response response = new ResponseImpl();
		response.description(description);
		response.example(example);
		response.mimeType(mimeType);
		exampleResponses.put(status.code(), response);
		exampleResponseClasses.put(status.code(), example.getClass());
		return this;
	}

	@Override
	public ApiRoute exampleRequest(String mimeType, String bodyText, String description) {
		Request request = new RequestImpl();
		request.description(description);
		request.body(bodyText);
		request.mimeType(mimeType);
		this.exampleRequests.put(mimeType, request);
		return this;
	}

	@Override
	public ApiRoute traits(String... traits) {
		this.traits = traits;
		return this;
	}

	@Override
	public String[] traits() {
		return traits;
	}

	@Override
	public Map<Integer, Response> exampleResponses() {
		return exampleResponses;
	}

	@Override
	public Map<String, Request> exampleRequests() {
		return exampleRequests;
	}

	@Override
	public String pathRegex() {
		return pathRegex;
	}

	@Override
	public HttpMethod method() {
		return method;
	}

	@Override
	public Map<String, QueryParameter> queryParameters() {
		return queryParameters;
	}

	@Override
	public ApiRoute queryParameter(String key, String description, String example) {
		QueryParameter param = new QueryParameterImpl(key);
		param.description(description);
		param.example(example);
		queryParameters.put(key, param);
		return this;
	}

	/**
	 * Convert the provided vertx path to a RAML path.
	 * 
	 * @param path
	 * @return RAML Path which contains '{}' instead of ':' characters
	 */
	protected String convertPath(String path) {
		StringBuilder builder = new StringBuilder();
		String[] segments = path.split("/");
		for (int i = 0; i < segments.length; i++) {
			String segment = segments[i];
			if (segment.startsWith(":")) {
				segment = "{" + segment.substring(1) + "}";
			}
			builder.append(segment);
			if (i != segments.length - 1) {
				builder.append("/");
			}
		}
		if (path.endsWith("/")) {
			builder.append("/");
		}
		return builder.toString();
	}

	// @Override
	// public Class<? extends RestModel> getExampleRequestClass() {
	// return exampleRequestClass;
	// }

	@Override
	public ApiRoute events(Event... events) {
		this.events.addAll(Arrays.asList(events));
		return this;
	}

	/**
	 * Return list of events for the endpoint.
	 * 
	 * @return
	 */
	@Override
	public Set<Event> events() {
		return events;
	}

	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<HttpMethod> methods() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Route setRegexGroupsNames(List<String> groups) {
		// TODO Auto-generated method stub
		return null;
	}

}
