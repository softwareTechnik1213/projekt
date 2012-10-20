package de.htwg.madn.view;

import de.htwg.madn.util.observer.IObserver;

public interface IUserInterface extends IObserver {

    boolean iterate();
}
