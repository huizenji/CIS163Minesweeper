package project2;

import java.util.*;

public class MineSweeperGame {
    private Cell[][] board;
    private GameStatus status;
    private int mineCount;
    private int boardSize;

    public MineSweeperGame(int boardSize, int mineCount) {
        status = GameStatus.NotOverYet;
        this.boardSize = boardSize;
        this.mineCount = mineCount;
        board = new Cell[boardSize][boardSize];
        setEmpty();
        layMines(mineCount);
        countMineNeighborsOfAllCells();
    }

    private void setEmpty() {
        for (int r = 0; r < boardSize; r++)
            for (int c = 0; c < boardSize; c++)
                board[r][c] = new Cell(false, false, false);
    }

    public Cell getCell(int row, int col) {
        return board[row][col];
    }

    public void select(int row, int col) {
        board[row][col].setExposed(true);

        if (board[row][col].isMine())   // did I lose
            status = GameStatus.Lost;
        if (board[row][col].getMineCount() == 0)
           // revealEmptyCellsRecursive(row, col);
            revealEmptyCellsNonRecursive(row, col);
        if (everyNonMineCellExposed()) { // every cell exposed?
            status = GameStatus.WON;    // did I win
        }
    }

    public GameStatus getGameStatus() {
        return status;
    }

    public void reset() {
        status = GameStatus.NotOverYet;
        setEmpty();
        layMines(mineCount);
        countMineNeighborsOfAllCells();
    }

    private void layMines(int mineCount) {
        int i = 0;        // ensure all mines are set in place

        Random random = new Random();
        while (i < mineCount) {
            int c = random.nextInt(boardSize);
            int r = random.nextInt(boardSize);

            if (!board[r][c].isMine()) {
                board[r][c].setMine(true);
                i++;
            }
        }
    }

    /******************************************************************
     * This method counts the number of mines directly surrounding the
     * indicated cell while making sure no outOfBounds exceptions are
     * thrown
     * @param row the indicated row of the board array
     * @param col the indicated column of the board array
     * @return the count of mines directly surrounding the indicated
     *          cell
     *****************************************************************/
    public int neighborCount(int row, int col) {
        int count = 0;
        for (int scannedRow = row - 1; scannedRow <= row + 1;
             scannedRow++) {
            for (int scannedCol = col - 1; scannedCol <=
                    col + 1; scannedCol++) {
                if ((scannedRow >= 0 && scannedRow < board.length)
                        && (scannedCol >= 0 && scannedCol < board[row].length)) {
                    if (board[scannedRow][scannedCol].isMine())
                        count++;
                }
            }
        }
        return count;
    }

    public void countMineNeighborsOfAllCells() {
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                if (!board[r][c].isMine())
                    board[r][c].setMineCount(neighborCount(r, c));
            }
        }
    }

    private boolean everyNonMineCellExposed() {
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                if (!board[r][c].isMine())
                    if (!board[r][c].isExposed())
                        return false;
            }
        }
        return true;
    }

   private void exposeEightSurroundingCells(int row, int col) {
        if (board[row][col].getMineCount() == 0) {
            for (int scannedRow = row - 1; scannedRow <= row + 1;
                 scannedRow++) {
                for (int scannedCol = col - 1; scannedCol <=
                        col + 1; scannedCol++) {
                    if ((scannedRow >= 0 && scannedRow < board.length)
                            && (scannedCol >= 0 && scannedCol < board[row].length)) {
                        if (!board[scannedRow][scannedCol].isFlagged())
                            board[scannedRow][scannedCol].setExposed(true);
                    }
                }
            }
        }
    }
    
    private boolean nonRecIsDone(int row, int col) {
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                if (board[r][c].isExposedNonRec()) {
                    for (int scannedRow = row - 1; scannedRow <= row + 1;
                         scannedRow++) {
                        for (int scannedCol = col - 1; scannedCol <=
                                col + 1; scannedCol++) {
                            if ((scannedRow >= 0 && scannedRow < board.length)
                                    && (scannedCol >= 0 && scannedCol < board[row].length)) {
                                if (!board[scannedRow][scannedCol].isExposedNonRec())
                                    return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
        
    public void revealEmptyCellsNonRecursive(int row, int col) {
        board[row][col].setExposedNonRec(true); //marked
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) { //scan whole board
                if (board[r][c].isExposedNonRec()) { //only continue if cell is marked
                    for (int scannedRow = row - 1; scannedRow <= row + 1; //from here
                         scannedRow++) {
                        for (int scannedCol = col - 1; scannedCol <=
                                col + 1; scannedCol++) {
                            if ((scannedRow >= 0 && scannedRow < board.length)
                                    && (scannedCol >= 0 && scannedCol < board[row].length)) { //to here will scan eight surround
                                board[scannedRow][scannedCol].setExposed(true); // expose it :)
                                if (board[scannedRow][scannedCol].getMineCount() == 0) { //oh? You need to be marked?
                                    //maybe another if to make sure it hasn't already been marked?
                                    board[scannedRow][scannedCol].setExposedNonRec(true); //marked
                                    if (!nonRecIsDone(scannedRow, scannedCol)) { //unmarked cell within the radius of another marked?
                                        r = 0;   //reset board scan to go again
                                        c = 0;      // if nonRecIsDone == false, then board scan will continue as normal until loop completes
                                        System.out.println("notDone"); //test
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Done"); //test
    }

     private void revealEmptyCellsRecursive(int row, int col) {
        if (!board[row][col].isFlagged()) {
            exposeEightSurroundingCells(row, col);
            board[row][col].setExposedRec(true);
            for (int scannedRow = row - 1; scannedRow <= row + 1;
                 scannedRow++) {
                for (int scannedCol = col - 1; scannedCol <=
                        col + 1; scannedCol++) {
                    if ((scannedRow >= 0 && scannedRow < board.length)
                            && (scannedCol >= 0 && scannedCol < board[row].length)) {
                        if (board[scannedRow][scannedCol].getMineCount() == 0) {
                            if (!board[scannedRow][scannedCol].isExposedRec())
                                revealEmptyCellsRecursive(scannedRow, scannedCol);
                        }
                    }
                }
            }
        }
    }
    
}
