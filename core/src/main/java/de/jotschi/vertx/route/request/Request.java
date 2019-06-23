package de.jotschi.vertx.route.request;

import java.util.Map;

import de.jotschi.vertx.route.header.Header;

public interface Request {

	Request description(String description);

	String description();

	Request headers(Map<String, Header> headers);

	Object body();

	Request body(Object example);

	String mimeType();

	Request mimeType(String mimeType);
}
