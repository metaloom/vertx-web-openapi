package de.jotschi.vertx.route.request.impl;

import java.util.Map;

import de.jotschi.vertx.route.header.Header;
import de.jotschi.vertx.route.request.Request;

public class RequestImpl implements Request {

	private String description;

	private Object example;

	private String mimeType;

	@Override
	public Request description(String description) {
		this.description = description;
		return this;
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public Request headers(Map<String, Header> headers) {
		return this;
	}

	@Override
	public Object body() {
		return example;
	}

	@Override
	public Request body(Object example) {
		this.example = example;
		return this;
	}

	@Override
	public String mimeType() {
		return mimeType;
	}

	@Override
	public Request mimeType(String mimeType) {
		this.mimeType = mimeType;
		return this;
	}

}
