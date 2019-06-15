package de.jotschi.vertx.event.impl;

import de.jotschi.vertx.event.Event;

public class EventImpl implements Event {

	private String address;

	private Object example;

	private String description;

	@Override
	public String address() {
		return address;
	}

	@Override
	public Event address(String address) {
		this.address = address;
		return this;
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public Object example() {
		return example;
	}

}
