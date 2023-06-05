package io.metaloom.vertx.router.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.metaloom.vertx.route.ApiRoute;
import io.metaloom.vertx.router.ApiRouter;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.AllowForwardHeaders;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public abstract class AbstractApiRouter implements ApiRouter {

	protected ApiRouter parent;

	protected final Router router;

	protected String description;

	protected String label;

	private List<RouterEntry> subRouters = new ArrayList<>();

	private List<ApiRoute> apiRoutes = new ArrayList<>();

	public AbstractApiRouter(Router router) {
		this.router = router;
	}

	public AbstractApiRouter(Vertx vertx) {
		this(Router.router(vertx));
	}

	@Override
	public Router getDelegate() {
		return router;
	}

	@Override
	public ApiRoute route() {
		return wrap(getDelegate().route());
	}

	@Override
	public ApiRoute route(HttpMethod method, String path) {
		return wrap(getDelegate().route(method, path));
	}

	@Override
	public ApiRoute route(String path) {
		System.out.println("Adding route for {" + path + "}");
		return wrap(getDelegate().route(path));
	}

	@Override
	public ApiRoute routeWithRegex(HttpMethod method, String regex) {
		return wrap(getDelegate().routeWithRegex(method, regex));
	}

	@Override
	public ApiRoute routeWithRegex(String regex) {
		return wrap(getDelegate().routeWithRegex(regex));
	}

	@Override
	public ApiRoute get() {
		return wrap(getDelegate().get());
	}

	@Override
	public ApiRoute get(String path) {
		return wrap(getDelegate().get(path));
	}

	@Override
	public ApiRoute getWithRegex(String regex) {
		return wrap(getDelegate().getWithRegex(regex));
	}

	@Override
	public ApiRoute head() {
		return wrap(getDelegate().head());
	}

	@Override
	public ApiRoute head(String path) {
		return wrap(getDelegate().head(path));
	}

	@Override
	public ApiRoute headWithRegex(String regex) {
		return wrap(getDelegate().headWithRegex(regex));
	}

	@Override
	public ApiRoute options() {
		return wrap(getDelegate().options());
	}

	@Override
	public ApiRoute options(String path) {
		return wrap(getDelegate().options(path));
	}

	@Override
	public ApiRoute optionsWithRegex(String regex) {
		return wrap(getDelegate().optionsWithRegex(regex));
	}

	@Override
	public ApiRoute put() {
		return wrap(getDelegate().put());
	}

	@Override
	public Route put(String path) {
		return wrap(getDelegate().put(path));
	}

	@Override
	public ApiRoute putWithRegex(String regex) {
		return wrap(getDelegate().putWithRegex(regex));
	}

	@Override
	public ApiRoute post() {
		return wrap(getDelegate().post());
	}

	@Override
	public ApiRoute post(String path) {
		return wrap(getDelegate().post(path));
	}

	@Override
	public ApiRoute postWithRegex(String regex) {
		return wrap(getDelegate().postWithRegex(regex));
	}

	@Override
	public ApiRoute delete() {
		return wrap(getDelegate().delete());
	}

	@Override
	public ApiRoute delete(String path) {
		return wrap(getDelegate().delete(path));
	}

	@Override
	public ApiRoute deleteWithRegex(String regex) {
		return wrap(getDelegate().deleteWithRegex(regex));
	}

	@Override
	public ApiRoute trace() {
		return wrap(getDelegate().trace());
	}

	@Override
	public ApiRoute trace(String path) {
		return wrap(getDelegate().trace(path));

	}

	@Override
	public Route traceWithRegex(String regex) {
		return wrap(getDelegate().traceWithRegex(regex));
	}

	@Override
	public ApiRoute connect() {
		return wrap(getDelegate().connect());
	}

	@Override
	public ApiRoute connect(String path) {
		return wrap(getDelegate().connect(path));
	}

	@Override
	public ApiRoute connectWithRegex(String regex) {
		return wrap(getDelegate().connectWithRegex(regex));
	}

	@Override
	public ApiRoute patch() {
		return wrap(getDelegate().patch());
	}

	@Override
	public ApiRoute patch(String path) {
		return wrap(getDelegate().patch(path));
	}

	@Override
	public ApiRoute patchWithRegex(String regex) {
		return wrap(getDelegate().patchWithRegex(regex));
	}

	@Override
	public List<Route> getRoutes() {
		return getDelegate().getRoutes().stream().map(r -> ApiRoute.create(r)).collect(Collectors.toList());
	}

	@Override
	public List<ApiRoute> getApiRoutes() {
		return apiRoutes;
	}

	@Override
	public List<RouterEntry> getSubRouters() {
		return subRouters;
	}

	@Override
	public ApiRouter clear() {
		getDelegate().clear();
		return this;
	}

	@Override
	public ApiRoute mountSubRouter(String mountPoint, Router subRouter) {
		Route route = getDelegate().mountSubRouter(mountPoint, subRouter);
		if (subRouter instanceof ApiRouter) {
			subRouters.add(new RouterEntry(mountPoint, (ApiRouter) subRouter));
		} else {
			// TODO automatically wrap the router
			throw new RuntimeException("Only resource routers can be mounted");
		}
		return wrap(route);
	}

	@Override
	public ApiRouter errorHandler(int statusCode, Handler<RoutingContext> errorHandler) {
		getDelegate().errorHandler(statusCode, errorHandler);
		return this;
	}

	@Override
	public void handleContext(RoutingContext context) {
		getDelegate().handleContext(context);

	}

	@Override
	public void handleFailure(RoutingContext context) {
		getDelegate().handleFailure(context);
	}

	@Override
	public void handle(HttpServerRequest event) {
		getDelegate().handle(event);
	}

	@Override
	public String label() {
		return label;
	}

	@Override
	public ApiRouter label(String label) {
		this.label = label;
		return this;
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public Map<String, Object> metadata() {
		return getDelegate().metadata();
	}

	@Override
	public ApiRouter description(String description) {
		this.description = description;
		return this;
	}

	private ApiRoute wrap(Route route) {
		ApiRoute endpointRoute = ApiRoute.create(route);
		apiRoutes.add(endpointRoute);
		return endpointRoute;
	}

	@Override
	public ApiRouter allowForward(AllowForwardHeaders allowForwardHeaders) {
		router.allowForward(allowForwardHeaders);
		return this;
	}

	@Override
	public ApiRouter modifiedHandler(Handler<Router> handler) {
		router.modifiedHandler(handler);
		return this;
	}

	@Override
	public ApiRouter putMetadata(String key, Object value) {
		router.putMetadata(key, value);
		return this;
	}

}
