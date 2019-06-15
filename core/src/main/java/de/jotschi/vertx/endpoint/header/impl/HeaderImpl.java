package de.jotschi.vertx.endpoint.header.impl;

import de.jotschi.vertx.endpoint.header.Header;

public class HeaderImpl implements Header {

	private String description;

	@Override
	public Header example(String example) {
		return this;
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

}
