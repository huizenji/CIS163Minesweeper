package project2;

import java.util.*;

/**********************************************************************
 * A class that stores all of the methods, rules, and functions of the
 * MineSweeper game
 *
 * Jillian Huizenga and Lauren Freeman
 * 25 February 2019
 *********************************************************************/
public class MineSweeperGame {

    /** a double array of cells **/
    private Cell[][] board;

    /** an object of GameStatus **/
    private GameStatus status;

    /** an integer that stores the mine count **/
    private int mineCount;

    /** an integer that stores the board size **/
    private int boardSize;

    /** a boolean that determines how to reveal cells **/
    private boolean useRecursion;

    /******************************************************************
     * A constructor of the MineSweeperGame class that initializes
     * the board
     * @param boardSize stores the desired board size (int)
     * @param mineCount stores the desired mine count (int)
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
     * (exposed, exposedRec, marked, mine, flagged) to false
     *****************************************************************/
    private void setEmpty() {
        for (int r = 0; r < boardSize; r++)
            for (int c = 0; c < boardSize; c++)
                board[r][c] = new Cell(false, false, false, false,
                        false);
    }

    /******************************************************************
     * This method returns a Cell for a given row and col on the board
     * so the panel can make the appropriate display
     * @param row stores an integer representing a given row (int)
     * @param col stores and integer representing a given column (int)
     * @return a cell object
     *****************************************************************/
    public Cell getCell(int row, int col) {
        return board[row][col];
    }

    /******************************************************************
     * A private method that returns whether to use recursion or
     * non-recursion to reveal empty cells
     * @return a boolean called useRecursion
     *****************************************************************/
    private boolean useRecursion() {
        return useRecursion;
    }

    /******************************************************************
     * A method called within MineSweeperPanel that sets whether to use
     * recursion or non-recursion to reveal empty cells
     * @param useRecursion an indicated boolean, true for recursion,
     *                      false for non-recursion
     *****************************************************************/
    public void setUseRecursion(boolean useRecursion) {
        this.useRecursion = useRecursion;
    }

    /******************************************************************
     * This method is called from the MineSweeperPanel class and is
     * invoked when the user has selected a JButton within the 2-Dim
     * array at location row, col.  This method marks that cell.
     * (contains inner comments)
     * @param row the first dimensional location of the Cell object
     *            (int)
     * @param col the second dimensional location of the Cell object
     *            (int)
     *****************************************************************/
    public void select(int row, int col) {

        // cannot lose on first selection, move mine
        if (MineSweeperPanel.actionCounter == 0) {
            moveMine(row, col);
            countMineNeighborsOfAllCells();
        }

        board[row][col].setExposed(true);

        // did I lose (clicked a mine)
        if (board[row][col].isMine())
            status = GameStatus.Lost;

        // reveal cells using recursion or non-recursion
        if (board[row][col].getMineCount() == 0 && !board[row][col].isMine()) {
            if (useRecursion())
                revealEmptyCellsRecursive(row, col);
            else
                revealEmptyCellsNonRecursive(row, col);
        }

        // did I win (every non-mine exposed)
        if (everyNonMineCellExposed()) {
            status = GameStatus.WON;
        }
    }

    /******************************************************************
     * A private method that searches for a new cell (starting in the
     * upper left hand corner) to place a mine if first cell clicked
     * contains a mine
     * @param row the selected cell's first dimensional position (int)
     * @param col the selected cell's second dimensional position (int)
     *****************************************************************/
    private void moveMine(int row, int col) {
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

    /******************************************************************
     * This method is called from the MineSweeperPanel class and it
     * determines if a player has won the game after the select method
     * is called
     * @return the current status of the game (won, lost, notOverYet)
     *****************************************************************/
    public GameStatus getGameStatus() {
        return status;
    }

    /******************************************************************
     * This method is called from the MineSweeperPanel class and it
     * resets the board to a new game
     *****************************************************************/
    public void reset() {
        MineSweeperPanel.actionCounter = 0;
        status = GameStatus.NotOverYet;
        setEmpty();
        layMines(mineCount);
    }

    /******************************************************************
     * A private method that randomly assigns mines to cells within the
     * board according to the indicated mineCount
     * @param mineCount the desired number of mines (int)
     *****************************************************************/
    private void layMines(int mineCount) {
        int i = 0;
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
     * This private method counts the number of mines directly
     * surrounding the indicated cell while making sure no outOfBounds
     * exceptions are thrown
     * @param row the indicated row of the board array (int)
     * @param col the indicated column of the board array (int)
     * @return the count of mines directly surrounding the indicated
     *          cell (int)
     *****************************************************************/
    private int neighborCount(int row, int col) {
        int count = 0;
        for (int scannedRow = row - 1; scannedRow <= row + 1;
             scannedRow++) {
            for (int scannedCol = col - 1; scannedCol <=
                    col + 1; scannedCol++) {
                if ((scannedRow >= 0 && scannedRow < board.length)
                        && (scannedCol >= 0 && scannedCol <
                        board[row].length)) {
                    if (board[scannedRow][scannedCol].isMine())
                        count++;
                }
            }
        }
        return count;
    }

    /******************************************************************
     * This private method counts the number of neighboring mines for
     * all cells and assigns them with the value
     *****************************************************************/
    private void countMineNeighborsOfAllCells() {
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                if (!board[r][c].isMine())
                    board[r][c].setMineCount(neighborCount(r, c));
            }
        }
    }

    /******************************************************************
     * A private method that determines if a player has won the game.
     * It determines if all cells not containing mines have been
     * exposed
     * @return a boolean the represents if the player has won the game
     *****************************************************************/
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

    /******************************************************************
     * A private method called by revealEmptyCellsRecursive that
     * exposes the eight cells directly surrounding the indicated cell
     * @param row the first dimensional location of the cell (int)
     * @param col the second dimensional location of the cell (int)
     *****************************************************************/
    private void exposeEightSurroundingCells(int row, int col) {
        if (board[row][col].getMineCount() == 0) {
            for (int scannedRow = row - 1; scannedRow <= row + 1;
                 scannedRow++) {
                for (int scannedCol = col - 1; scannedCol <=
                        col + 1; scannedCol++) {
                    if ((scannedRow >= 0 && scannedRow < board.length)
                            && (scannedCol >= 0 && scannedCol <
                            board[row].length)) {
                        if (!board[scannedRow][scannedCol].isFlagged())
                            board[scannedRow][scannedCol]
                                    .setExposed(true);
                    }
                }
            }
        }
    }

    /******************************************************************
     * If the selected cell is blank (i.e., a cell’s mine count is
     * zero), this will show all blank cells (zero mine count)
     * connected to the current cell until you find a non-blank
     * (Non-Recursive)
     * (contains inner comments)
     * @param startingRow the selected cell's first dimensional
     *                    location (int)
     * @param startingCol the selected cell's second dimensional
     *                    location (int)
     *****************************************************************/
    private void revealEmptyCellsNonRecursive(int startingRow,
                                              int startingCol) {

        // marked means the cell needs attention (mC = 0, not exposed)
        board[startingRow][startingCol].setMarked(true);
        int r = 0;
        int c = 0;

        // scan the board for a mark
        while (r < boardSize) {
            while (c < boardSize) {

                // found a mark?
                if (board[r][c].isMarked()) {

                    // then look at everything around it
                    for (int scannedRow = r - 1; scannedRow <= r + 1;
                         scannedRow++) {
                        for (int scannedCol = c - 1; scannedCol <=
                                c + 1; scannedCol++) {
                            if ((scannedRow >= 0 && scannedRow <
                                    board.length)
                                    && (scannedCol >= 0 && scannedCol <
                                    board[r].length)) {

                                // does it need to be marked?
                                if (board[scannedRow][scannedCol]
                                        .getMineCount() == 0) {
                                    if (!board[scannedRow][scannedCol]
                                            .isExposed()) {
                                        if (!board[scannedRow]
                                                [scannedCol]
                                                .isFlagged())

                                            // then mark it, we'll be
                                            // back for it later
                                            board[scannedRow]
                                                    [scannedCol]
                                                    .setMarked(true);
                                    }
                                } else

                                    // no mark needed? just expose
                                    if (!board[scannedRow][scannedCol]
                                            .isFlagged())
                                        board[scannedRow][scannedCol]
                                                .setExposed(true);
                            }
                        }
                    }

                    // un-mark the original cell and expose it
                    board[r][c].setExposed(true);
                    board[r][c].setMarked(false);

                    // re-scan the board for marks
                    r = 0;
                    c = 0;

                    // if cell is not marked, then keep searching
                } else {
                    c++;
                }
            }
            if (c == boardSize) {
                c = 0;
                r++;
            }
        }
        // loop will end when all marked cells have been attended to
    }

    /******************************************************************
     * If the selected cell is blank (i.e., a cell’s mine count is
     * zero), this method will show all blank cells (zero mine count)
     * connected to the current cell until you find a non-blank
     * (Recursive)
     * @param row the selected cell's first dimensional location (int)
     * @param col the selected cell's second dimensional location (int)
     *****************************************************************/
    private void revealEmptyCellsRecursive(int row, int col) {
        if (!board[row][col].isFlagged()) {
            exposeEightSurroundingCells(row, col);
            board[row][col].setExposedRec(true);
            for (int scannedRow = row - 1; scannedRow <= row + 1;
                 scannedRow++) {
                for (int scannedCol = col - 1; scannedCol <=
                        col + 1; scannedCol++) {
                    if ((scannedRow >= 0 && scannedRow < board.length)
                            && (scannedCol >= 0 && scannedCol < 
                                board[row].length)) {
                        if (board[scannedRow][scannedCol]
                            .getMineCount() == 0) {
                            if (!board[scannedRow][scannedCol]
                                .isExposedRec())
                                revealEmptyCellsRecursive(scannedRow,
                                                          scannedCol);
                        }
                    }
                }
            }
        }
    }


}
