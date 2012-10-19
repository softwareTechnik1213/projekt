package de.htwg.madn.model;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FinishContainerTest {
	Player p1;
	FinishContainer fc;

	@Before
	public void setUp() throws Exception {
		p1 = new Player(1, Color.red, "p1");

	}

	@Test
	public void testFinishContainer() {
		fc = new FinishContainer(p1);
		assertSame(p1, fc.getOwner());
	}

	@Test
	public void testGetFieldsList() {
		FinishContainer f = new FinishContainer(p1);
		Field field = new Field();
		f.getFieldsList().add(field);
		assertSame(field, f.getFieldsList().get(0));

	}

}
