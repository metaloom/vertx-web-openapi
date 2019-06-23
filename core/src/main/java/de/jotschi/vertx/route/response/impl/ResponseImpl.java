package de.jotschi.vertx.route.response.impl;

import java.util.Map;

import de.jotschi.vertx.route.header.Header;
import de.jotschi.vertx.route.response.Response;

public class ResponseImpl implements Response {

	private String description;

	private Object example;

	private String mimeType;

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

	@Override
	public Object example() {
		return example;
	}

	@Override
	public Response example(Object example) {
		this.example = example;
		return this;
	}
	
	@Override
	public String mimeType() {
		return mimeType;
	}

	@Override
	public Response mimeType(String mimeType) {
		this.mimeType = mimeType;
		return this;
	}


}
