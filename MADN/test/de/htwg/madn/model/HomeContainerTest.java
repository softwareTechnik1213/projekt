package de.htwg.madn.model;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

public class HomeContainerTest {

	Player p1;
	Player p2;
	HomeContainer fc;

	@Before
	public void setUp() throws Exception {
		p1 = new Player(1, Color.red, "p1");
		p2 = new Player(1, Color.red, "p2");

	}

	@Test
	public void testHomeContainer() {
		fc = new HomeContainer(p1);
		fc.setOwner(p2);
		assertSame(p2, fc.getOwner());
	}

	@Test
	public void testGetFieldsList() {
		HomeContainer f = new HomeContainer(p1);
		Field field = new Field();
		f.getFieldsList().add(field);
		assertSame(field, f.getFieldsList().get(0));

	}
	

}
