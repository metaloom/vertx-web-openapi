package io.metaloom.vertx.route.response.impl;

import java.util.HashMap;
import java.util.Map;

import io.metaloom.vertx.route.response.Response;

public class ResponseImpl implements Response {

	private String description;

	private Map<String, String> headers = new HashMap<>();

	private Object example;

	private String mimeType;

	public ResponseImpl(String mimeType) {
		this.mimeType = mimeType;
	}

	public ResponseImpl() {
	}

	public static Response response(String mimeType) {
		return new ResponseImpl(mimeType);
	}

	public static Response response() {
		return new ResponseImpl();
	}

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
	public Object body() {
		return example;
	}

	@Override
	public Response body(Object example) {
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

	@Override
	public Map<String, String> headers() {
		return headers;
	}

	@Override
	public Response header(String name, String value) {
		headers.put(name, value);
		return this;
	}

}
