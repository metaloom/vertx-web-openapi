package de.jotschi.vertx.route.response;

import java.util.Map;

/**
 * Describes a HTTP response.
 */
public interface Response {

	/**
	 * Set the description for the response.
	 * 
	 * @param description
	 * @return
	 */
	Response description(String description);

	/**
	 * Return the description of the response.
	 * 
	 * @return
	 */
	String description();

	/**
	 * Return the example response body.
	 * 
	 * @return
	 */
	Object body();

	/**
	 * Set the example response body.
	 * 
	 * @param body
	 * @return
	 */
	Response body(Object body);

	/**
	 * Return the mimeType of the content of the response.
	 * 
	 * @return
	 */
	String mimeType();

	/**
	 * Set the mimeType of the response content.
	 * 
	 * @param mimeType
	 * @return
	 */
	Response mimeType(String mimeType);

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
	Response header(String name, String value);

}
