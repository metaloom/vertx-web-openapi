package de.jotschi.vertx.endpoint.response.impl;

import java.util.Map;

import de.jotschi.vertx.endpoint.header.Header;
import de.jotschi.vertx.endpoint.response.Response;

public class ResponseImpl implements Response {

	private String description;

	@Override
	public Response description(String description) {
		this.description = description;
		return this;
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public Response headers(Map<String, Header> headers) {
		return this;
	}

}
