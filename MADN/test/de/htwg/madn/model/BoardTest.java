package de.htwg.madn.model;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class BoardTest {

    @Test
    public void testBoard() {
        Board b = new Board();
        assertNotNull(b.toString());
    }
}
