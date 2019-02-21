package project2;

import javax.swing.*;
import java.util.Random;

public class MineSweeperGame {
    private Cell[][] board;
    private GameStatus status;
    private static int totalMineCount;
    private static String totalMineCountStr = "";
    private static int boardSize;
    private static String boardSizeStr = "";

    public MineSweeperGame() {
        status = GameStatus.NotOverYet;
        boardSize = 10;
        totalMineCount = 10;
        board = new Cell[boardSize][boardSize];
        setEmpty();
        layMines(totalMineCount);

    }

    public static int getBoardSize() {
        return boardSize;
    }

    public static void promptBoardSize() {
        do {
            if (boardSizeStr.length() > 0) {
                JOptionPane.showMessageDialog(null, "Please choose a" +
                        " number between 3 and 30", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            boardSizeStr = JOptionPane.showInputDialog(null, "Enter " +
                    "in the size of the board (3-30): ");
        } while (boardSizeStr.equals("") ||
                Integer.parseInt(boardSizeStr) < 3 || Integer.parseInt(boardSizeStr) > 30);

        boardSize = Integer.parseInt(boardSizeStr);
    }

    public static void promptNumberOfMines() {
        totalMineCountStr = JOptionPane.showInputDialog(null, "Enter in " +
                "the desired number of mines (default is 10): ");
        if (Integer.parseInt(totalMineCountStr) >= 1 &&
                Integer.parseInt(totalMineCountStr) < Integer.parseInt(boardSizeStr))
            totalMineCount = Integer.parseInt(totalMineCountStr);
    }

    private void setEmpty() {
        for (int r = 0; r < boardSize; r++)
            for (int c = 0; c < boardSize; c++)
                board[r][c] = new Cell(false, false, false);  // totally clear.
    }

	public Cell getCell(int row, int col) {
		return board[row][col];
	}

	public void select(int row, int col) {
		board[row][col].setExposed(true);

		if (board[row][col].isMine())   // did I lose
			status = GameStatus.Lost;
		else { // every cell exposed?

			status = GameStatus.WON;    // did I win

		}
	}

	public GameStatus getGameStatus() {
		return status;
	}

	public void reset() {
		status = GameStatus.NotOverYet;
		setEmpty();
		layMines (10);
	}

	private void layMines(int mineCount) {
		int i = 0;		// ensure all mines are set in place

		Random random = new Random();
		while (i < mineCount) {			// perhaps the loop will never end :)
			int c = random.nextInt(10);
			int r = random.nextInt(10);

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
        if (!board[row][col].isMine()) {
            for (int scannedRow = row - 1; scannedRow >= row + 1;
                 scannedRow--) {
                for (int scannedCol = col - 1; scannedCol <=
                        col + 1; scannedCol++) {
                    if ((row > 0 || row < board.length) && (col > 0 || col < board[row].length))
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
	
}




	//  a non-recursive from .... it would be much easier to use recursion
