package de.htwg.madn.model;

import java.util.ArrayList;
import java.util.List;

public final class PublicContainer implements IFieldContainer {

    private List<Field> fields = new ArrayList<Field>();

    public PublicContainer(int fieldsPerContainer) {
        for (int i = 0; i < fieldsPerContainer; i++) {
            fields.add(new Field());
        }
    }

    @Override
    public List<Field> fieldList() {
        return fields;
    }

    /**
     * unused fields (like spacers) are ' '
     * @return
     */
    public char[][] toCharArray() {
        int rows = 11;
        int cols = 11;
        char[][] ret = new char[rows][cols];
        char c = '+';
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ret[i][j] = ' ';
            }
        }

        // not enough fields.. return empty array
        if (fields.size() < 40) {
            return ret;
        }
   
        // top top
        ret[0][4] = fields.get(0).toChar(c);
        ret[0][5] = fields.get(1).toChar(c);
        ret[0][6] = fields.get(2).toChar(c);
        // top right
        ret[1][6] = fields.get(3).toChar(c);
        ret[2][6] = fields.get(4).toChar(c);
        ret[3][6] = fields.get(5).toChar(c);
        ret[4][6] = fields.get(6).toChar(c);
        // right top
        ret[4][7] = fields.get(7).toChar(c);
        ret[4][8] = fields.get(8).toChar(c);
        ret[4][9] = fields.get(9).toChar(c);
        ret[4][10] = fields.get(10).toChar(c);
        // right right
        ret[5][10] = fields.get(11).toChar(c);
        ret[6][10] = fields.get(12).toChar(c);
        // right bottom
        ret[6][9] = fields.get(13).toChar(c);
        ret[6][8] = fields.get(14).toChar(c);
        ret[6][7] = fields.get(15).toChar(c);
        ret[6][6] = fields.get(16).toChar(c);
        // bottom right
        ret[7][6] = fields.get(17).toChar(c);
        ret[8][6] = fields.get(18).toChar(c);
        ret[9][6] = fields.get(19).toChar(c);
        ret[10][6] = fields.get(20).toChar(c);
        // bottom bottom
        ret[10][5] = fields.get(21).toChar(c);
        ret[10][4] = fields.get(22).toChar(c);
        // bottom left
        ret[9][4] = fields.get(23).toChar(c);
        ret[8][4] = fields.get(24).toChar(c);
        ret[7][4] = fields.get(25).toChar(c);
        ret[6][4] = fields.get(26).toChar(c);
        // left bottom
        ret[6][3] = fields.get(27).toChar(c);
        ret[6][2] = fields.get(28).toChar(c);
        ret[6][1] = fields.get(29).toChar(c);
        ret[6][0] = fields.get(30).toChar(c);
        // left left
        ret[5][0] = fields.get(31).toChar(c);
        ret[4][0] = fields.get(32).toChar(c);
        // left top
        ret[4][1] = fields.get(33).toChar(c);
        ret[4][2] = fields.get(34).toChar(c);
        ret[4][3] = fields.get(35).toChar(c);
        ret[4][4] = fields.get(36).toChar(c);
        // top left
        ret[3][4] = fields.get(37).toChar(c);
        ret[2][4] = fields.get(38).toChar(c);
        ret[1][4] = fields.get(39).toChar(c);

        return ret;
    }
}
