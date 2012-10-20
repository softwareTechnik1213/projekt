package de.htwg.madn.model;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

public class PublicContainerTest {

	Player p1;
	Player p2;

	@Before
	public void setUp() throws Exception {
		p1 = new Player(1, Color.red, "p1");
		p2 = new Player(1, Color.red, "p2");

	}

	@Test
	public void testGetFieldsList() {
		PublicContainer f = new PublicContainer();
		Field field = new Field();
		f.fieldsList().add(field);
		assertSame(field, f.fieldsList().get(0));

	}

}
