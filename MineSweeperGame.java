package project2;


import java.util.Random;

public class MineSweeperGame {
	private Cell[][] board;
	private GameStatus status;
	private int totalMineCount = 10;

	public MineSweeperGame() {
		status = GameStatus.NotOverYet;
		board = new Cell[10][10];
		setEmpty();
		layMines (7);
		
	}

	private void setEmpty() {
		for (int r = 0; r < 10; r++)
			for (int c = 0; c < 10; c++)
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

    private boolean isNotBorder(int row, int col) {
        return (row > 0 && row < board.length - 1 && col > 0 && col < board[row].length - 1);
    }

    private boolean isLeftBorder(int row, int col) {
        return (row > 0 && row < board.length - 1 && col == 0);
    }

   private boolean isRightBorder(int row, int col) {
        return (row > 0 && row < board.length - 1 && col == board[row].length - 1);
    }

   private boolean isTopBorder(int row, int col) {
        return (row == 0 && col > 0 && col < board[row].length - 1);
    }

  private boolean isBottomBorder(int row, int col) {
        return (row == board.length - 1 && col > 0 && col < board[row].length - 1);
    }

    private boolean isTopLeftCorner(int row, int col) {
        return (row == 0 && col == 0);
    }

    private boolean isTopRightCorner(int row, int col) {
        return (row == 0 && col == board[row].length - 1);
    }

    private boolean isBottomLeftCorner(int row, int col) {
        return (row == board.length - 1 && col == 0);
    }

    private boolean isBottomRightCorner(int row, int col) {
        return (row == board.length - 1 && col == board[row].length - 1);
    }

    /******************************************************************
     * This method counts the number of mines directly surrounding the
     * indicated cell while making sure no outOfBounds exceptions are
     * thrown
     * @param row the indicated row of the board array
     * @param col the indicated column of the board array
     * @return the count of mines directly surrounding the indicated cell
     *****************************************************************/
    public int neighborCount(int row, int col) {
        int count = 0;
        if (!board[row][col].isMine()) {
            if (isNotBorder(row, col)) {
                for (int scannedRow = row + 1; scannedRow >= row - 1;
                     scannedRow--) {
                    for (int scannedCol = col - 1; scannedCol <=
                            col + 1; scannedCol++) {
                        if (board[scannedRow][scannedCol].isMine())
                            count++;
                    }
                }
            }else if (isLeftBorder(row, col)) {
                for (int scannedRow = row + 1; scannedRow >= row - 1;
                     scannedRow--) {
                    for (int scannedCol = col; scannedCol <=
                            col + 1; scannedCol++) {
                        if (board[scannedRow][scannedCol].isMine())
                            count++;
                    }
                }
            }else if (isRightBorder(row, col)) {
                for (int scannedRow = row + 1; scannedRow >= row - 1;
                     scannedRow--) {
                    for (int scannedCol = col - 1; scannedCol <=
                            col; scannedCol++) {
                        if (board[scannedRow][scannedCol].isMine())
                            count++;
                    }
                }
            }else if (isTopBorder(row, col)) {
                for (int scannedRow = row; scannedRow >= row - 1;
                     scannedRow--) {
                    for (int scannedCol = col - 1; scannedCol <=
                            col; scannedCol++) {
                        if (board[scannedRow][scannedCol].isMine())
                            count++;
                    }
                }
            }else if (isBottomBorder(row, col)) {
                for (int scannedRow = row + 1; scannedRow >= row;
                     scannedRow--) {
                    for (int scannedCol = col - 1; scannedCol <=
                            col; scannedCol++) {
                        if (board[scannedRow][scannedCol].isMine())
                            count++;
                    }
                }
            }else if (isTopLeftCorner(row, col)) {
                for (int scannedRow = row; scannedRow >= row - 1;
                     scannedRow--) {
                    for (int scannedCol = col; scannedCol <=
                            col + 1; scannedCol++) {
                        if (board[scannedRow][scannedCol].isMine())
                            count++;
                    }
                }
            }else if (isTopRightCorner(row, col)) {
                for (int scannedRow = row; scannedRow >= row - 1;
                     scannedRow--) {
                    for (int scannedCol = col - 1; scannedCol <=
                            col; scannedCol++) {
                        if (board[scannedRow][scannedCol].isMine())
                            count++;
                    }
                }
            }else if (isBottomLeftCorner(row, col)) {
                for (int scannedRow = row - 1; scannedRow >= row;
                     scannedRow--) {
                    for (int scannedCol = col; scannedCol <=
                            col + 1; scannedCol++) {
                        if (board[scannedRow][scannedCol].isMine())
                            count++;
                    }
                }
            }else if (isBottomRightCorner(row, col)) {
                for (int scannedRow = row - 1; scannedRow >= row;
                     scannedRow--) {
                    for (int scannedCol = col - 1; scannedCol <=
                            col; scannedCol++) {
                        if (board[scannedRow][scannedCol].isMine())
                            count++;
                    }
                }
            }
        }
        return count;
    }
	
}




	//  a non-recursive from .... it would be much easier to use recursion
