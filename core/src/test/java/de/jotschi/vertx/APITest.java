package de.jotschi.vertx;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class APITest {

	@Test
	public void testRouting() {
		TestResource root = new TestResource();
		assertEquals("Resource used for testing", root.description());
	}
}
