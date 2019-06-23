package de.jotschi.vertx.route.response;

import java.util.Map;

import de.jotschi.vertx.route.header.Header;

public interface Response {

	Response description(String description);

	String description();

	Response headers(Map<String, Header> headers);

	Object example();

	Response example(Object example);

	String mimeType();

	Response mimeType(String mimeType);

}
