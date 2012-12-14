package de.htwg.madn.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.htwg.madn.controller.MovementController.AutonomeMovementController;
import de.htwg.madn.model.Dice;
import de.htwg.madn.model.IGameSettings;
import de.htwg.madn.model.IModelPort;

public class MovementControllerTest {

	private final Dice dice;
	private final IGameSettings settings;
	private String status;
	private final IModelPort model;
	public MovementController 
	@Before
	public void setUp() throws Exception {

			this.model = model;
			this.dice = model.getDice();
			this.settings = model.getSettings();
			this.status = "";
			this.autonomeComtroller = new AutonomeMovementController();
	}

	@Test
	public void testMovementController() {
		fail("Not yet implemented");
	}

	@Test
	public void testThrowDice() {
		fail("Not yet implemented");
	}

	@Test
	public void testHasActiveFigures() {
		fail("Not yet implemented");
	}

	@Test
	public void testMoveFigure() {
		fail("Not yet implemented");
	}

	@Test
	public void testStartAutonomeRun() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStatusString() {
		fail("Not yet implemented");
	}

}
