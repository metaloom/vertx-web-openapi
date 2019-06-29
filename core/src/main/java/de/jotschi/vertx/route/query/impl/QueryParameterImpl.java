package de.jotschi.vertx.route.query.impl;

import de.jotschi.vertx.route.query.QueryParameter;

public class QueryParameterImpl implements QueryParameter {

	protected String description;

	private String key;

	private Object example;

	public QueryParameterImpl(String key) {
		this.key = key;
	}

	@Override
	public void description(String description) {
		this.description = description;
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public String key() {
		return key;
	}

	@Override
	public void example(Object example) {
		this.example = example;
	}

	@Override
	public Object example() {
		return example;
	}

}
