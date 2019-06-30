package de.jotschi.vertx.route.request;

import java.util.Map;

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
	 * @return
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
	 * @return
	 */
	Request body(Object example);

	/**
	 * Return the mimeType of the request body.
	 * 
	 * @return
	 */
	String mimeType();

	Request mimeType(String mimeType);

	/**
	 * Return the header of the response.
	 * 
	 * @return
	 */
	Map<String, String> headers();

	/**
	 * Set the header for the response.
	 * 
	 * @param name
	 * @param value
	 * @return
	 */
	Request header(String name, String value);
}
