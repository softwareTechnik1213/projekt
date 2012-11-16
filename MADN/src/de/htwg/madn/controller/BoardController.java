package de.htwg.madn.controller;

import de.htwg.madn.model.Board;
import de.htwg.madn.model.Field;
import de.htwg.madn.model.HomeContainer;
import de.htwg.madn.model.Player;
import de.htwg.madn.util.observer.Observable;
import java.awt.Color;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

public final class BoardController extends Observable {

    private Board board;
    private String status = "";
    private static final String WELCOME_STRING = "Neue Spiel gestartet.";
    private int lastDiceNumber;
    private Player lastDiceThrowerPlayer;
    private Player activePlayer;
    // without finished players
    private Deque<Player> activePlayersQueue;
    private static final int DICE_MIN = 1;
    private static final int DICE_MAX = 6;
    private static final int MIN_PLAYERS = 2;
    private boolean gameIsRunning;

    public BoardController(Board b) {
        board = b;
        reset();
    }

    public String getBoardString() {
        String display;
        if (activePlayer != null) {
            display = board.toString() + "\n" + "Spieler "
                    + activePlayer.getName() + " ist am Zug.\n" + "STATUS: "
                    + status + "\n";
        } else {
            display = board.toString() + "\nSTATUS: " + status + "\n";
        }
        return display;
    }

    public void addPlayer(String name, Color col) {
        Player newPlayer = board.addPlayer(name, col);
        if (newPlayer == null) {
            status = "Zu viele Spieler. Maximum: " + board.getMaxPlayers();
        } else {
            HomeContainer hc = getNextFreeHomeContainer();
            hc.setOwner(newPlayer);
            occupyAllFields(hc, newPlayer);
            activePlayersQueue.push(newPlayer);
            status = "Spieler " + newPlayer.getId() + ": " + newPlayer.getName() + " hinzugefuegt.";
        }

        notifyObservers();
    }

    private void occupyAllFields(HomeContainer hc, Player p) {
        char[] figureCodes = p.getFigureCodes();
        int i = 0;
        for (Field f : hc.fieldList()) {
            f.setOccupier(p, figureCodes[i]);
            i++;
        }
    }

    private HomeContainer getNextFreeHomeContainer() {
        for (HomeContainer hc : board.getHomeContainers()) {
            if (hc.getOwner() == null) {
                return hc;
            }
        }
        return null;
    }

    public void reset() {
        board = new Board();
        setInitialState();
        notifyObservers();
    }

    private void setInitialState() {
        activePlayersQueue = new LinkedList<Player>();
        lastDiceThrowerPlayer = null;
        activePlayer = null;
        status = WELCOME_STRING;
        gameIsRunning = false;
    }

    public void throwDice() {
        if (isAllowedToThrowDice()) {
            lastDiceNumber = getRandomNumber(DICE_MIN, DICE_MAX);
            lastDiceThrowerPlayer = activePlayer;
            status = "Gewuerfelt: " + lastDiceNumber;
        } else {
            status = "Du hast schon gewuerfelt: " + lastDiceNumber;
        }
        notifyObservers();
    }

    private boolean isAllowedToThrowDice() {
        if (activePlayer != null && lastDiceThrowerPlayer == activePlayer) {
            return false;
        }
        return true;
    }

    public void moveFigure(char figure) {
        if (lastDiceThrowerPlayer != activePlayer) {
            status = "Du solltest zuerst wuerfeln!";
            notifyObservers();
            return;
        }
        if (!isValidFigureForActivePlayer(figure)) {
            status = "Diese Figur gehoert dir nicht!";
            notifyObservers();
            return;
        }

        // CHECK IF FIGURE IS ALLOWED TO MOVE!!
        // AND THROW OUT OTHER FIGURES AND SO ON
        Field currentField = null;

        int i = 0;
        for (Field field : board.getPublicFieldsList()) {
            if (field.getFigure() == figure) {
                currentField = field;
                break;
            }
            i++;
        }

        HomeContainer homeContainer = null;
        if (currentField == null) {
            // figure not in public field.
            // search in home field
            i = 0;
            for (HomeContainer container : board.getHomeContainers()) {
                if (container.getOwner() == activePlayer) {
                    homeContainer = container;
                    break;
                }
                i++;
            }

            // figure is in a home field

            for (Field field : homeContainer.fieldList()) {
                if (field.getFigure() == figure) {
                    currentField = field;
                    currentField.removeOccupier();
                    board.getPublicFieldsList().get(board.INDEX_START_HC[i]).setOccupier(activePlayer, figure);
                    break;
                }
            }

        } else {
            currentField.removeOccupier();
            board.getPublicFieldsList().get((i + lastDiceNumber) % board.getMaxPublicFields()).setOccupier(activePlayer, figure);
        }


        status = "Spielfigur " + figure + " bewegt.";

        setNextActivePlayer();

        notifyObservers();
    }

    public void startGame() {
        if (gameIsRunning) {
            status = "Spiel laeuft schon!";
        } else if (activePlayersQueue.size() < MIN_PLAYERS) {
            status = "Zu wenige Spieler. Mindestens " + MIN_PLAYERS
                    + " benoetigt.";
        } else {
            setNextActivePlayer();
            String name = activePlayer.getName();
            status = "Spiel beginnt. Spieler " + name + " faengt an.";
        }
        notifyObservers();
    }

    private int getRandomNumber(int min, int max) {
        Random rand = new Random();
        return min + Math.abs(rand.nextInt()) % max;
    }

    private void setNextActivePlayer() {
        // get from tail and remove
        activePlayer = activePlayersQueue.pollLast();
        // and then push to head of queue
        activePlayersQueue.push(activePlayer);
    }

    private boolean isValidFigureForActivePlayer(char figure) {
        for (char c : activePlayer.getFigureCodes()) {
            if (c == figure) {
                return true;
            }
        }
        return false;
    }
}
