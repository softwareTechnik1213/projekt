package de.htwg.madn.util.observer;

import java.util.LinkedList;
import java.util.List;

public class Observable implements IObservable {

    private List<IObserver> observer = new LinkedList<IObserver>();

    @Override
    public void addObserver(IObserver s) {
        observer.add(s);
    }

    @Override
    public void removeObserver(IObserver s) {
        observer.remove(s);
    }

    @Override
    public void removeAllObservers() {
        observer.clear();
    }

    @Override
    public void notifyObservers() {
        for (IObserver ob : observer) {
            ob.update();
        }
    }
}
