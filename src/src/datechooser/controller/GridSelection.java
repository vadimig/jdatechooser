/*
 * GridSelection.java
 *
 * Created on 3 »юль 2006 г., 22:36
 *
*/

package datechooser.controller;

import java.io.Serializable;

/**
 * Incapsulates info about selected cell (stores row and column).<br>
 * »нкапсулирует информацию о выбранной €чейке (хранит строку и столбец).
 * @author Androsov Vadim
 * @since 1.0
 */
public class GridSelection implements Serializable {
    
    private int row;
    private int column;
    
    public GridSelection() {
        this(0, 0);
    }

    public GridSelection(int row, int column) {
        set(row, column);
    }

    public void set(int row, int column) {
        setRow(row);
        setColumn(column);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
    
    public String toString() {
        return "Position [row = " + getRow() + 
                ", column = " + getColumn() + "]";
    }
}
