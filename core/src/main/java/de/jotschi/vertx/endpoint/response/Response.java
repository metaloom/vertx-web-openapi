package de.jotschi.vertx.endpoint.response;

import java.util.Map;

import de.jotschi.vertx.endpoint.header.Header;

public interface Response {

	Response description(String description);

	String description();

	Response headers(Map<String, Header> headers);

}
