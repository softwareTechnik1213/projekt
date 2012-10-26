package de.htwg.madn.util.observer;

import java.util.LinkedList;
import java.util.List;

public class Observable {

    private List<IObserver> observer = new LinkedList<IObserver>();

    public void addObserver(IObserver s) {
        observer.add(s);
    }

    public void removeObserver(IObserver s) {
        observer.remove(s);
    }

    public void removeAllObservers() {
        observer.clear();
    }

    public void notifyObservers() {
        for (IObserver ob : observer) {
            ob.update();
        }
    }
}
