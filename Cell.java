package project2;


/**********************************************************************
 * A class that sets and stores the four statuses of a single Cell
 * as well as the number of mines surrounding it.
 *
 * Jillian Huizenga and Lauren Freeman
 * CIS 163-01 Professor Ferguson
 * 26 February 2019
 *********************************************************************/

public class Cell {
    
    //the number of mines surrounding the cell
    private int mineCount;
    
    //whether the cell is flagged as possibly containing a mine
    private boolean isFlagged;
    
    //whether the cell has been exposed (clicked on or otherwise)
    private boolean isExposed;
    
    //whether the cell has been exposed (for the recursion method)
    private boolean isExposedRec;
    
    //whether the cell has been marked (for the non-recursion method)
    private boolean isMarked;
    
    //whether the cell contains a mine
    private boolean isMine;

    /**********************************************************************
     * Constructor method to instantiate the cell, setting whether it's 
     * been exposed, marked, flagged, or if it's a mine.
     *
     * @param exposed true if the cell has been exposed
     * @param exposedRec true if the cell has been exposed for the purpose
     *                   of revealEmptyCellsRecursive()
     * @param marked true if the cell has been marked to be acted upon by
     *               revealEmptyCellsNonRecursive()
     * @param mine true if the cell contains a mine
     * @param flagged true if the player has flagged the mine
     *********************************************************************/
    public Cell(boolean exposed, boolean exposedRec, boolean marked,
                boolean mine, boolean flagged) {
        
        //set all of the variables
        isExposed = exposed;
        isExposedRec = exposedRec;
        isMarked = marked;
        isMine = mine;
        isFlagged = flagged;
    }

    /******************************************************************
     * Returns the number of mines immediately surrounding the cell.
     * 
     * @return mineCount the number of mines around the cell
     *****************************************************************/
    public int getMineCount() {
        return mineCount;
    }

    /******************************************************************
     * Sets the number of mines immediately surrounding the cell.
     *****************************************************************/
    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    }

    /******************************************************************
     * Returns whether or not the cell has been marked as flagged.
     *
     * @return isFlagged true if the cell is marked as flagged
     *****************************************************************/
    public boolean isFlagged() {
        return isFlagged;
    }

    /******************************************************************
     * Sets whether or not the cell has been marked as flagged
     *****************************************************************/
    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    /******************************************************************
     * Returns whether or not the cell has been exposed.
     *
     * @return isExposed true if the cell is exposed
     *****************************************************************/
    public boolean isExposed() {
        return isExposed;
    }
    
    /******************************************************************
     * Sets whether or not the cell has been exposed.
     *****************************************************************/
    public void setExposed(boolean exposed) {
        isExposed = exposed;
    }

    /******************************************************************
     * Returns whether or not the cell has been exposed by the 
     * revealEmptyCellsRecursive method, to prevent an infinite loop.
     *
     * @return isExposed true if the cell is exposed
     *****************************************************************/
    public boolean isExposedRec() {
        return isExposedRec;
    }

    /******************************************************************
     * Sets whether or not the cell has been exposed by the 
     * revealEmptyCellsRecursive method, to prevent an infinite loop.
     *****************************************************************/
    public void setExposedRec(boolean exposedRec) {
        isExposedRec = exposedRec;
    }

    /******************************************************************
     * Returns whether or not the cell has been marked by the 
     * revealEmptyCellsNonRecursive method, which marks cells that
     * need the cells around them to be revealed.
     *
     * @return isExposed true if the cell is marked
     *****************************************************************/
    public boolean isMarked() {
        return isMarked;
    }

    /******************************************************************
     * Sets whether or not the cell has been marked by the 
     * revealEmptyCellsNonRecursive method, which marks cells that
     * need the cells around them to be revealed.
     *****************************************************************/
    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    /******************************************************************
     * Returns whether or not the cell contains a mine.
     *
     * @return isMine true if the cell has a mine
     *****************************************************************/
    public boolean isMine() {
        return isMine;
    }

    /******************************************************************
     * Sets whether or not the cell contains a mine.
     *****************************************************************/
    public void setMine(boolean mine) {
        isMine = mine;
    }

}
