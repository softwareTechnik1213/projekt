package de.htwg.madn.model;

import java.util.LinkedList;
import java.util.List;


public final class Board {

    private List<HomeContainer> hclist = new LinkedList<HomeContainer>();
    private List<FinishContainer> fclist = new LinkedList<FinishContainer>();
    private PublicContainer pc;
    private final int players = 4;
    private final int figuresPerPlayer = 4;
    private final int publicFields = 40;

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
        int rows = 11;
        int cols = 11;

        char[][] field = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                field[i][j] = getFieldChar(i, j);
            }
        }

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

    private char getFieldChar(int row, int col) {
        char spaceField = ' ';
        char publicField = '+';
        char homeField = '*';
        char finishField = '#';
        char ret = '@'; // if @ is returned we have an error in our method!

        /* plan:

            ---11x11---

            **  +++  **
            **  +#+  **
                +#+
                +#+
            +++++#+++++
            +#### ####+
            +++++#+++++
                +#+
                +#+
            **  +#+  **
            **  +++  **

            40 public fields!


            where the top left is start.
            then it goes right and down.
            START ------------>
                  ^           |
                  |           |
                  |           V
                  <------------

            This direction applies to all field containers as well


         */

        // get cell type
        if (!isPlayableField(row, col)) {
            ret = spaceField;
        } else if (isOccupiedField(row, col)) {
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
        return (row == 0 || row == 1 || row == 9 || row == 10)
                && (col == 0 || col == 1 || col == 9 || col == 10);
    }

    private boolean isPublicField(int row, int col) {
        return ((col == 4 || col == 6) && row != 5)
                || ((row == 4 || row == 6) && col != 5)
                || (col == 5 && (row == 0 || row == 10))
                || (row == 5 && (col == 0 || col == 10));
    }

    private boolean isFinishField(int row, int col) {
        return (col == 5 && row >= 1 && row <= 9 && row != 5)
                || (row == 5 && col >= 1 && col <= 9 && col != 5);
    }

    private boolean isPlayableField(int row, int col) {
        return isHomeField(row, col) || isPublicField(row, col)
                || isFinishField(row, col);
    }

    private boolean isOccupiedField(int row, int col) {
        if (!isPlayableField(row, col)) {
            return false;
        }

        // check systematically all fields if someone is on it

        if (isHomeField(row, col)) {
            return checkOccupiedHomeField(row, col);
        } else if (isFinishField(row, col)) {
            return checkOccupiedFinishField(row, col);
        } else if (isPublicField(row, col)) {
            return checkOccupiedPublicField(row, col);
        } else {
            throw new IllegalStateException("this shouldn't happen");
        }
    }

    // this method expects that row, col refers to a home field!
    private boolean checkOccupiedHomeField(int row, int col) {
        // go to the correct home container

        /*  home container numeration

         0          1


         3          2


         */
        int targetContainer;
        if (col >= 0 && col <= 1 && row >= 0 && row <= 1) {
            targetContainer = 0;
        } else if (col >= 9 && col <= 10 && row >= 0 && row <= 1) {
            targetContainer = 1;
        } else if (col >= 9 && col <= 10 && row >= 9 && row <= 10) {
            targetContainer = 2;
        } else {
            targetContainer = 3;
        }

        int i = 0;
        HomeContainer p = null;
        for (HomeContainer hc : hclist) {
            p = hc;
            if (i == targetContainer) {
                break;
            }
            i++;
        }
        if (p == null) {
            throw new IllegalStateException();
        }

        // now get the right field in p (which points to the right home container)
        /*
            0 1
            3 2
        */
        int targetField;

        // top row of each container
        if (row == 0 || row == 9) {
            // first one or second one?
            if (col == 0 || col == 9) {
                targetField = 0;
            } else {
                targetField = 1;
            }
        } else { // bottom row of each container
            // first one or second one?
            if (col == 0 || col == 9) {
                targetField = 3;
            } else {
                targetField = 2;
            }
        }

        int j = 0;
        Field q = null;
        for (Field f : p.fieldList()) {
            q = f;
            if (j == targetField) {
                break;
            }
            j++;
        }
        if (q == null) {
            throw new IllegalStateException();
        }

        return q.getOccupier() != null;
    }

    // this method expects that row, col refers to a finish field!
    private boolean checkOccupiedFinishField(int row, int col) {

        /*
          finish field numeration

           0

           #
           #
           #
           #
     3 #### #### 1
           #
           #
           #
           #

           2

         */

        // get target container
        int targetContainer;

        if (col == 5 && row <= 4) {
            targetContainer = 0;
        } else if (row == 5 && col >= 6) {
            targetContainer = 1;
        } else if (row == 5 && col <= 4) {
            targetContainer = 2;
        } else {
            targetContainer = 3;
        }

        int i = 0;
        FinishContainer p = null;
        for (FinishContainer fc : fclist) {
            p = fc;
            if (i == targetContainer) {
                break;
            }
            i++;
        }
        if (p == null) {
            throw new IllegalStateException();
        }

        // now get the correct field

        /*
              #0
              #1
              #2
              #3
          0123 3210
          #### ####
              #3
              #2
              #1
              #0
         */

        int targetField;

        // calculate the margin from the edges
        if (col == 5 && row <= 4) { // top container
            targetField = row - 1;
        } else if (col == 5 && row >= 6) { // bottom container
            targetField = 10 - row - 1;
        } else if (row == 5 && col <= 4) { // left container
            targetField = col - 1;
        }  else { // right container
            targetField = 10 - col - 1;
        }

        int j = 0;
        Field q = null;
        for (Field f : p.fieldList()) {
            q = f;
            if (j == targetField) {
                break;
            }
            j++;
        }
        if (q == null) {
            throw new IllegalStateException();
        }

        return q.getOccupier() != null;
    }

    // this method expects that row, col refers to a public field!
    private boolean checkOccupiedPublicField(int row, int col) {

        /*
           field numeration:
                Start point
                |
                V
            **  012  **
            **  +#3  **
                +#4
                +#5
            +++++#6789+  and so on
            +#### ####+
            +++++#+++++ <-- 12
                +#+
                +#+
            **  +#+  **
            **  +++  **


         */

        // get target field
        int targetField;

        if (row == 0) { // top row
            targetField = row - 4;
        } else if (col == 6 && row <= 4) { // top right col including both corners
            targetField = 2 + row;
        } else if (row == 4 && col >= 7) { // right top row including right corner

        }







        return false;
    }
}
