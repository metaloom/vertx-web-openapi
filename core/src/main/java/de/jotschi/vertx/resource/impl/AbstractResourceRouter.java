package de.jotschi.vertx.resource.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.jotschi.vertx.endpoint.EndpointRoute;
import de.jotschi.vertx.endpoint.resource.ResourceRouter;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public abstract class AbstractResourceRouter implements ResourceRouter {

	protected ResourceRouter parent;

	protected final Router router;

	protected String description;

	protected String label;

	private List<RouterEntry> subRouters = new ArrayList<>();

	private List<EndpointRoute> endpointRoutes = new ArrayList<>();

	public AbstractResourceRouter(Router router) {
		this.router = router;
	}

	public AbstractResourceRouter(Vertx vertx) {
		this(Router.router(vertx));
	}

	@Override
	public Router getDelegate() {
		return router;
	}

	@Override
	public EndpointRoute route() {
		return wrap(getDelegate().route());
	}

	@Override
	public EndpointRoute route(HttpMethod method, String path) {
		return wrap(getDelegate().route(method, path));
	}

	@Override
	public EndpointRoute route(String path) {
		System.out.println("Adding route for {" + path + "}");
		return wrap(getDelegate().route(path));
	}

	@Override
	public EndpointRoute routeWithRegex(HttpMethod method, String regex) {
		return wrap(getDelegate().routeWithRegex(method, regex));
	}

	@Override
	public EndpointRoute routeWithRegex(String regex) {
		return wrap(getDelegate().routeWithRegex(regex));
	}

	@Override
	public EndpointRoute get() {
		return wrap(getDelegate().get());
	}

	@Override
	public EndpointRoute get(String path) {
		return wrap(getDelegate().get(path));
	}

	@Override
	public EndpointRoute getWithRegex(String regex) {
		return wrap(getDelegate().getWithRegex(regex));
	}

	@Override
	public EndpointRoute head() {
		return wrap(getDelegate().head());
	}

	@Override
	public EndpointRoute head(String path) {
		return wrap(getDelegate().head(path));
	}

	@Override
	public EndpointRoute headWithRegex(String regex) {
		return wrap(getDelegate().headWithRegex(regex));
	}

	@Override
	public EndpointRoute options() {
		return wrap(getDelegate().options());
	}

	@Override
	public EndpointRoute options(String path) {
		return wrap(getDelegate().options(path));
	}

	@Override
	public EndpointRoute optionsWithRegex(String regex) {
		return wrap(getDelegate().optionsWithRegex(regex));
	}

	@Override
	public EndpointRoute put() {
		return wrap(getDelegate().put());
	}

	@Override
	public Route put(String path) {
		return wrap(getDelegate().put(path));
	}

	@Override
	public EndpointRoute putWithRegex(String regex) {
		return wrap(getDelegate().putWithRegex(regex));
	}

	@Override
	public EndpointRoute post() {
		return wrap(getDelegate().post());
	}

	@Override
	public EndpointRoute post(String path) {
		return wrap(getDelegate().post(path));
	}

	@Override
	public EndpointRoute postWithRegex(String regex) {
		return wrap(getDelegate().postWithRegex(regex));
	}

	@Override
	public EndpointRoute delete() {
		return wrap(getDelegate().delete());
	}

	@Override
	public EndpointRoute delete(String path) {
		return wrap(getDelegate().delete(path));
	}

	@Override
	public EndpointRoute deleteWithRegex(String regex) {
		return wrap(getDelegate().deleteWithRegex(regex));
	}

	@Override
	public EndpointRoute trace() {
		return wrap(getDelegate().trace());
	}

	@Override
	public EndpointRoute trace(String path) {
		return wrap(getDelegate().trace(path));

	}

	@Override
	public Route traceWithRegex(String regex) {
		return wrap(getDelegate().traceWithRegex(regex));
	}

	@Override
	public EndpointRoute connect() {
		return wrap(getDelegate().connect());
	}

	@Override
	public EndpointRoute connect(String path) {
		return wrap(getDelegate().connect(path));
	}

	@Override
	public EndpointRoute connectWithRegex(String regex) {
		return wrap(getDelegate().connectWithRegex(regex));
	}

	@Override
	public EndpointRoute patch() {
		return wrap(getDelegate().patch());
	}

	@Override
	public EndpointRoute patch(String path) {
		return wrap(getDelegate().patch(path));
	}

	@Override
	public EndpointRoute patchWithRegex(String regex) {
		return wrap(getDelegate().patchWithRegex(regex));
	}

	@Override
	public List<Route> getRoutes() {
		return getDelegate().getRoutes().stream().map(r -> EndpointRoute.create(r)).collect(Collectors.toList());
	}

	@Override
	public List<EndpointRoute> getEndpointRoutes() {
		return endpointRoutes;
	}

	@Override
	public List<RouterEntry> getSubRouters() {
		return subRouters;
	}

	@Override
	public ResourceRouter clear() {
		getDelegate().clear();
		return this;
	}

	@Override
	public ResourceRouter mountSubRouter(String mountPoint, Router subRouter) {
		getDelegate().mountSubRouter(mountPoint, subRouter);
		if (subRouter instanceof ResourceRouter) {
			subRouters.add(new RouterEntry(mountPoint, (ResourceRouter) subRouter));
		} else {
			// TODO automatically wrap the router
			throw new RuntimeException("Only resource routers can be mounted");
		}
		return this;
	}

	@Override
	public ResourceRouter exceptionHandler(Handler<Throwable> exceptionHandler) {
		getDelegate().exceptionHandler(exceptionHandler);
		return this;
	}

	@Override
	public ResourceRouter errorHandler(int statusCode, Handler<RoutingContext> errorHandler) {
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
	public ResourceRouter label(String label) {
		this.label = label;
		return this;
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public ResourceRouter description(String description) {
		this.description = description;
		return this;
	}

	private EndpointRoute wrap(Route route) {
		EndpointRoute endpointRoute = EndpointRoute.create(route);
		endpointRoutes.add(endpointRoute);
		return endpointRoute;
	}

}
