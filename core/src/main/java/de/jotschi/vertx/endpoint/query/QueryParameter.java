package de.jotschi.vertx.endpoint.query;

public interface QueryParameter {

	void description(String description);

	String description();

	void example(String example);

	String key();

}
