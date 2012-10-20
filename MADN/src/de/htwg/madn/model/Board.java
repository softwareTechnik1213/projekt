package de.htwg.madn.model;

import java.util.LinkedList;
import java.util.List;


public final class Board {

    private List<HomeContainer> hclist = new LinkedList<HomeContainer>();
    private List<FinishContainer> fclist = new LinkedList<FinishContainer>();
    private PublicContainer pc;
    private final int players = 4;
    private final int figuresPerPlayer = 4;
    private final int publicFields = 28;

    public Board() {
        // create all home and finish field containers
        for (int i = 0; i < players; i++) {
            hclist.add(new HomeContainer(figuresPerPlayer));
            fclist.add(new FinishContainer(figuresPerPlayer));
        }
        // create a public field container
        pc = new PublicContainer(publicFields);
    }

    @Override
    public String toString() {
        char[][] field = new char[21][21];

        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = getFieldChar(i, j);
            }
        }

        StringBuilder sb = new StringBuilder(field.length * field.length);
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                sb.append(field[i][j]);
            }
            // add a line break after each line
            sb.append('\n');
        }
        return sb.toString();
    }

    private char getFieldChar(int row, int col) {
        char spaceField = ' ';
        char publicField = '+';
        char homeField = '*';
        char finishField = '#';
        char ret = '@'; // if @ is returned we have an error in our method!

        /* plan:

            -------21x21---------
              --5--       --5--
                    --5--

            **      + + +      **
            **      + # +      **
                    + # +
                    + # +
            + + + + + # + + + + +
            + # # # #   # # # # +
            + + + + + # + + + + +
                    + # +
                    + # +
            **      + # +      **
            **      + + +      **

            where the top left is start.
            then it goes right and down.
            START ------------>
                  ^           |
                  |           |
                  |           V
                  <------------


         */

        // get cell type
        if (!isPlayableField(row, col)) {
            ret = spaceField;
        } else if (isSetField(row, col)) {
            ret = 'X'; // should be a b c d e f.... according to figure no
        } else if (isHomeField(row, col)) {
            ret = homeField;
        } else if (isPublicField(row, col)) {
            ret = publicField;
        } else if (isFinishField(row, col)) {
            ret = finishField;
        }

        return ret;
    }

    private boolean isHomeField(int row, int col) {
        return (row == 0 || row == 1 || row == 19 || row == 20)
                && (col == 0 || col == 1 || col == 19 || col == 20);
    }

    private boolean isPublicField(int row, int col) {
        return true;
    }

    private boolean isFinishField(int row, int col) {
        return true;
    }

    private boolean isPlayableField(int row, int col) {
        return isHomeField(row, col) || isPublicField(row, col)
                || isFinishField(row, col);
    }

    private boolean isSetField(int row, int col) {
        if (!isPlayableField(row, col)) {
            return false;
        }

        return true;
    }
}
