package project2;

import java.util.*;

public class MineSweeperGame {

    /**
     * a double array of cells
     **/
    private Cell[][] board;

    /**
     * an object of GameStatus
     **/
    private GameStatus status;

    /**
     * an integer that stores the mine count
     **/
    private int mineCount;

    /**
     * an integer that stores the board size
     **/
    private int boardSize;

    private boolean wantsRecursion;

    /******************************************************************
     * A constructor of the MineSweeperGame class
     * @param boardSize stores the desired board size
     * @param mineCount stores the desired mine count
     *****************************************************************/
    public MineSweeperGame(int boardSize, int mineCount) {
        status = GameStatus.NotOverYet;
        this.boardSize = boardSize;
        this.mineCount = mineCount;
        board = new Cell[boardSize][boardSize];
        setEmpty();
        layMines(mineCount);
    }

    /******************************************************************
     * A private method that sets every cell's boolean conditions
     * (exposed, exposedRec, exposedNonRec, mine, flagged) to false
     *****************************************************************/
    private void setEmpty() {
        for (int r = 0; r < boardSize; r++)
            for (int c = 0; c < boardSize; c++)
                board[r][c] = new Cell(false, false, false, false, false);
    }

    /******************************************************************
     * A public method that returns a given cell in the board[][] array
     * @param row stores an integer representing the given row
     * @param col stores and integer representing the given column
     * @return a cell object
     *****************************************************************/
    public Cell getCell(int row, int col) {
        return board[row][col];
    }

    public boolean wantsRecursion() {
        return wantsRecursion;
    }

    public void setWantsRecursion(boolean wantsRecursion) {
        this.wantsRecursion = wantsRecursion;
    }

    public void select(int row, int col) {
        if (MineSweeperPanel.actionCounter == 0) {
            moveMine(row, col);
            countMineNeighborsOfAllCells();
        }

        board[row][col].setExposed(true);

        if (board[row][col].isMine())   // did I lose
            status = GameStatus.Lost;
        if (board[row][col].getMineCount() == 0) {
            if (wantsRecursion())
                revealEmptyCellsRecursive(row, col);
            else
                revealEmptyCellsNonRecursive(row, col);
        }
        if (everyNonMineCellExposed()) { // every cell exposed?
            status = GameStatus.WON;    // did I win
        }
    }

    public void moveMine(int row, int col) {
        if (board[row][col].isMine()) {
            board[row][col].setMine(false);
            for (int r = 0; r < board.length; r++) {
                for (int c = 0; c < board.length; c++) {
                    if (!board[r][c].isMine()) {
                        board[r][c].setMine(true);
                        break;
                    }
                }
                break;
            }
        }
    }

    public GameStatus getGameStatus() {
        return status;
    }

    public void reset() {
        MineSweeperPanel.actionCounter = 0;
        status = GameStatus.NotOverYet;
        setEmpty();
        layMines(mineCount);

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
    private int neighborCount(int row, int col) {
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

    private void countMineNeighborsOfAllCells() {
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

//    private boolean anyMarked() {
//        for (int r = 0; r < boardSize; r++) {
//            for (int c = 0; c < boardSize; c++) {
//                if (board[r][c].isMarked())
//                    return true;
//            }
//        }
//        return false;
//    }

    private void revealEmptyCellsNonRecursive(int startingRow, int startingCol) {
        board[startingRow][startingCol].setMarked(true);
        int r = 0;
        int c = 0;
        while (r < boardSize) {
            System.out.println("c at beginning of r loop: " + c);
            while (c < boardSize) {
                if (board[r][c].isMarked()) { //is it marked?
                    System.out.println("Mark exists");
                    for (int scannedRow = r - 1; scannedRow <= r + 1; //then look at everything around it
                         scannedRow++) {
                        for (int scannedCol = c - 1; scannedCol <=
                                c + 1; scannedCol++) {
                            if ((scannedRow >= 0 && scannedRow < board.length)
                                    && (scannedCol >= 0 && scannedCol < board[r].length)) {
                                if (board[scannedRow][scannedCol].getMineCount() == 0) { // need to be marked?
                                    if (!board[scannedRow][scannedCol].isExposed())
                                        board[scannedRow][scannedCol].setMarked(true); // then mark it, we'll be back for it later
                                }else
                                    board[scannedRow][scannedCol].setExposed(true);
                            }
                        }
                    }
                    board[r][c].setExposed(true); // we're done with you, un-mark
                    board[r][c].setMarked(false);
                    r = 0;
                    c = 0;
                } else {
                    c++;
                    System.out.println("c = " + c);
                }
            }
            if (c == boardSize) {
                System.out.println("Before this, c is " + c);
                c = 0;
                System.out.println("C is " + c);
                r++;
                System.out.println("r = " + r);
            }
        }
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
