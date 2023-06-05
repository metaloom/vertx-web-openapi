package io.metaloom.vertx.route.header.impl;

import io.metaloom.vertx.route.header.Header;

public class HeaderImpl implements Header {

	private String description;

	private Object example;

	private String name;

	@Override
	public Header example(Object example) {
		this.example = example;
		return this;
	}

	@Override
	public Object example() {
		return example;
	}

	@Override
	public Header description(String description) {
		this.description = description;
		return this;
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public Header name(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String name() {
		return name;
	}

}
