package de.htwg.madn.model;

import java.util.LinkedList;
import java.util.List;


public final class Board {

    private List<HomeContainer> hclist = new LinkedList<HomeContainer>();
    private List<FinishContainer> fclist = new LinkedList<FinishContainer>();
    private PublicContainer pc;

    public Board(int players, int figuresPerPlayer, int publicFields) {
        // create all home and finish field containers
        for (int i = 0; i < players; i++) {
            hclist.add(new HomeContainer(figuresPerPlayer));
            fclist.add(new FinishContainer(figuresPerPlayer));
        }
        // create a public field container
        pc = new PublicContainer(publicFields);
    }
}
