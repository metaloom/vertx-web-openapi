package io.metaloom.vertx.route.header;

public interface Header {

	/**
	 * Return the name of the header.
	 */
	String name();

	/**
	 * Set the name of the header.
	 * 
	 * @param name
	 * @return
	 */
	Header name(String name);

	/**
	 * Return the example value of the header.
	 * 
	 * @return
	 */
	Object example();

	/**
	 * Set the example value.
	 * 
	 * @param example
	 * @return
	 */
	Header example(Object example);

	/**
	 * Return the description of the header.
	 * 
	 * @return
	 */
	String description();

	/**
	 * Set the description of the header.
	 * 
	 * @param description
	 * @return
	 */
	Header description(String description);

}
