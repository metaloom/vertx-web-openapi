package io.metaloom.vertx.route.request;

import java.util.List;

import io.metaloom.vertx.route.header.Header;

/**
 * Describes an HTTP request.
 */
public interface Request {

	/**
	 * Return the description of the request.
	 * 
	 * @return
	 */
	String description();

	/**
	 * Set the description of the request.
	 * 
	 * @param description
	 * @return Fluent API
	 */
	Request description(String description);

	/**
	 * Return the request body.
	 * 
	 * @return
	 */
	Object body();

	/**
	 * Set the request body.
	 * 
	 * @param example
	 * @return Fluent API
	 */
	Request body(Object example);

	/**
	 * Return the mimeType of the request body.
	 * 
	 * @return
	 */
	String mimeType();

	/**
	 * Set the mimeType of the request body.
	 * 
	 * @param mimeType
	 * @return Fluent API
	 */
	Request mimeType(String mimeType);

	/**
	 * Return the header of the response.
	 * 
	 * @return
	 */
	List<Header> headers();

	/**
	 * Set the header for the response.
	 * 
	 * @param name
	 * @param description
	 * @param value
	 * @return Fluent API
	 */
	Request header(String name, String description, Object value);
}
