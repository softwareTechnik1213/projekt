package de.htwg.madn.util.observer;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class ObservableTest {

    private Observable observable;
    private IObserver obs;

    @Before
    public void setUp() {
        observable = new Observable();
        obs = new IObserver() {

            @Override
            public void update() {
                assertTrue(true);
            }
        };
        observable.addObserver(obs);
    }

    @Test
    public void testAddObserver() {
        observable.notifyObservers();
    }

    @Test
    public void testRemoveAllObservers() {
        observable.removeAllObservers();
    }

    @Test
    public void testRemoveObserver() {
        observable.removeObserver(obs);
    }
}
