package de.jotschi.vertx.route.query;

public interface QueryParameter {

	/**
	 * Set the description of the query parameter.
	 * 
	 * @param description
	 */
	void description(String description);

	/**
	 * Return the description of the query parameter.
	 * 
	 * @return
	 */
	String description();

	/**
	 * Set the example value.
	 * 
	 * @param example
	 */
	void example(Object example);

	/**
	 * Return the example value.
	 * 
	 * @return
	 */
	Object example();

	/**
	 * Return the key of the query parameter.
	 * 
	 * @return
	 */
	String key();

}
