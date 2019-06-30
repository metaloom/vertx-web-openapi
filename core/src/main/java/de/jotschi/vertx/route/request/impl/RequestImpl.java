package de.jotschi.vertx.route.request.impl;

import java.util.HashMap;
import java.util.Map;

import de.jotschi.vertx.route.request.Request;

public class RequestImpl implements Request {

	private String description;

	private Object example;

	private String mimeType;

	private Map<String, String> headers = new HashMap<>();

	public RequestImpl() {
	}

	public static Request request() {
		return new RequestImpl();
	}

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

	@Override
	public Map<String, String> headers() {
		return headers;
	}

	@Override
	public Request header(String name, String value) {
		headers.put(name, value);
		return this;
	}

}
