package de.jotschi.vertx.route.header.impl;

import de.jotschi.vertx.route.header.Header;

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
