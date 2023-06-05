package io.metaloom.vertx.event;

/**
 * An event contains descriptive information on a Vert.x eventbus event.
 */
public interface Event {

	/**
	 * Address used for the event.
	 * 
	 * @return
	 */
	String address();

	/**
	 * Description of the event.
	 * 
	 * @return
	 */
	String description();

	/**
	 * Example event payload.
	 * 
	 * @return
	 */
	Object example();

	/**
	 * Set the address of the event.
	 * 
	 * @param address
	 * @return
	 */
	Event address(String address);
}
