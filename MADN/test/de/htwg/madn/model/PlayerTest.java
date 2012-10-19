package de.htwg.madn.model;

import java.awt.Color;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import de.htwg.madn.model.Player;

public final class PlayerTest {

    private Player player;
    private int expectedId;
    private Color expectedColor;
    private String expectedName;

    @Before
    public void setUp() {
        expectedId = 1;
        expectedColor = Color.red;
        expectedName = "test player";
        player = new Player(expectedId, expectedColor, expectedName);
    }

    @Test
    public void testGetId() {
        assertTrue(player.getId() == expectedId);
    }

    @Test
    public void testGetColor() {
        assertEquals(player.getColor(), expectedColor);
    }

    @Test
    public void testGetName() {
        assertEquals(player.getName(), expectedName);
    }
}
