package de.htwg.madn.model;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

public final class Board {

    private List<HomeContainer> hclist = new LinkedList<HomeContainer>();
    private List<FinishContainer> fclist = new LinkedList<FinishContainer>();
    private PublicContainer pc;
    private static final int players = 4;
    private static final int figuresPerPlayer = 4;
    private static final int publicFields = 40;

    public Board() {
        // create all home and finish field containers
        for (int i = 0; i < players; i++) {
            hclist.add(new HomeContainer(figuresPerPlayer));
            fclist.add(new FinishContainer(figuresPerPlayer));
        }
        // create a public field container
        pc = new PublicContainer(publicFields);
        hclist.get(2).fieldList().get(1).setOccupier(new Player(2, Color.red, "test"), 'U');

    }

    @Override
    public String toString() {
        int rows = 11;
        int cols = 11;
        char[][] field = new char[rows][cols];

        // merge in public field
        mergeArray(field, pc.toCharArray(), 0, 0, rows, cols);

        // merge in home fields
        mergeArray(field, hclist.get(0).toCharArray(), 0, 0, 2, 2);
        mergeArray(field, hclist.get(1).toCharArray(), 0, 9, 2, 2);
        mergeArray(field, hclist.get(2).toCharArray(), 9, 9, 2, 2);
        mergeArray(field, hclist.get(3).toCharArray(), 9, 0, 2, 2);

        // merge in finish fields
        // horizontal
        mergeArray(field, fclist.get(3).toCharArray(), 5, 1, 1, 4);
        mergeArray(field, fclist.get(1).toCharArray(), 5, 6, 1, 4);
        // vertical - need transponation
        char[][] finishArray0 = fclist.get(0).toCharArray();
        char[][] finishArray2 = fclist.get(2).toCharArray();
        mergeArray(field, lineToColumnVector(finishArray0), 1, 5, 4, 1);
        mergeArray(field, lineToColumnVector(finishArray2), 6, 5, 4, 1);


        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(field[i][j]);
            }
            // add a line break after each line
            sb.append('\n');
        }
        return sb.toString();
    }

    private void mergeArray(char[][] result, char[][] source,
            int toRow, int toCol,
            int countRow, int countCol) {

        // maybe use System.arraycopy?
        for (int i = toRow, k = 0; i < toRow + countRow; i++, k++) {
            for (int j = toCol, l = 0; j < toCol + countCol; j++, l++) {
                result[i][j] = source[k][l];
            }
        }

    }

    private char[][] lineToColumnVector(char[][] arr) {
        int rows = arr[0].length;
        char[][] ret = new char[rows][1];

        for (int i = 0; i < rows; i++) {
            ret[i][0] = arr[0][i];
        }
        return ret;
    }
}
