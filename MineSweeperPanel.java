package project2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**********************************************************************
 * A class that sets up the display panel for the MineSweeper game.
 * It includes the actual game board, a quit button, a button to toggle
 * recursive and non-recursive zero fill, labels that display the
 * number of wins and losses, and a label that shows the number of
 * mines left.
 *
 * Jillian Huizenga and Lauren Freeman
 * CIS 163-01 Professor Ferguson
 * 26 February 2019
 *********************************************************************/

public class MineSweeperPanel extends JPanel {

    //array of buttons that make up the game board
    private JButton[][] board;

    //button that allows the player to quit the game
    private JButton quitButton = new JButton("QUIT");

    /*
     * button that allows the player to toggle whether zero fill is
     * handled recursively or non-recursively
     */
    private JButton recursiveButton 
            = new JButton("Toggle Recursion");

    //declares a cell object
    private Cell iCell;

    /*
     * size of the board, the number of mines in play, the number of
     * mines left to flag, the amount of wins, and the amount of losses
     */
    private int boardSize, mineCount, mineCountDisplay, wins, losses;

    /*
     *labels for the amount of wins, losses, mines, and whether the
     * zero fill is being handled recursively or non recursively
     */
    private JLabel winsLabel, lossesLabel, recursionLabel, mineLabel;

    //counts the amount of moves that have taken place
    public static int actionCounter = 0;

    //declares a new game
    private MineSweeperGame game;

    /******************************************************************
     * A constructor method that sets up the display panel. Creates and
     * adds mouseListeners to the game board and buttons, as well as 
     * all of the labels, and adds them to a panel with a grid layout.
     *
     * @param boardSize the dimensions for the rows and columns
     * @param mineCount the number of mines in the game
     *****************************************************************/
    public MineSweeperPanel(int boardSize, int mineCount) {

        //creates the two panels
        JPanel bottom = new JPanel();
        JPanel top = new JPanel();

        //creates a mouse listener for the top panel
        top.addMouseListener(new mouse());

        //adds mouse listeners to the buttons
        quitButton.addMouseListener(new mouse());
        recursiveButton.addMouseListener(new mouse());

        // create game, listeners
        mouse listener = new mouse();
        game = new MineSweeperGame(boardSize, mineCount);

        // create the board
        this.boardSize = boardSize;
        this.mineCount = mineCount;
        mineCountDisplay = mineCount;
        top.setLayout(new GridLayout(boardSize, boardSize));
        board = new JButton[boardSize][boardSize];

        //add listeners to each cell on the board
        for (int row = 0; row < boardSize; row++)
            for (int col = 0; col < boardSize; col++) {
                board[row][col] = new JButton("");
                board[row][col].addMouseListener(listener);
                top.add(board[row][col]);
            }

        //displays the game board
        displayBoard();

        //creates labels
        recursionLabel = new JLabel("Recursive Zero Fill");
        mineLabel = new JLabel("Mines Left: " + mineCountDisplay);

        //sets layout for bottom panel and adds buttons
        bottom.setLayout(new GridLayout(3, 2));
        bottom.add(quitButton);
        bottom.add(recursiveButton);

        //instantiates the win/loss labels, adds them to panel
        wins = 0;
        losses = 0;
        winsLabel = new JLabel("Wins: 0");
        lossesLabel = new JLabel("Losses: 0");
        bottom.add(winsLabel);
        bottom.add(lossesLabel);

        //adds recursion label and mine label to bottomm panel
        bottom.add(recursionLabel);
        bottom.add(mineLabel);

        // add all to contentPane
        add(new JLabel("!!!!  Mine Sweeper  !!!!"), 
                BorderLayout.NORTH);
        add(top, BorderLayout.NORTH);
        add(bottom, BorderLayout.SOUTH);

    }

    /******************************************************************
     * A private helper method that determines what is displayed on 
     * each cell in the game board. The cell is either empty, displays
     * the number of mines around it, displays a '!' if it's a mine, or
     * a '?' if it's marked as flagged.
     *****************************************************************/
    private void displayBoard() {
        //loops through all cells
        for (int r = 0; r < boardSize; r++)
            for (int c = 0; c < boardSize; c++) {
                iCell = game.getCell(r, c);

                //for a normal cell, display nothing if not enabled
                if (iCell.isExposed()) {
                    board[r][c].setEnabled(false);
                    //if exposed, surrounded by 0 mines, cell is blank
                    if (iCell.getMineCount() == 0) {
                        board[r][c].setText("");
                    }
                    //display number of mines surrounding if exposed
                    else
                        board[r][c].setText("" + iCell.getMineCount());
                } else
                    board[r][c].setEnabled(true);

                //if game is won, flag all mines
                if (game.getGameStatus() == GameStatus.WON) {
                    if (iCell.isMine()) {
                        iCell.setFlagged(true);
                    }
                }

                //cell displays '?' when flagged, mine count updated
                if(iCell.isFlagged()) {
                    board[r][c].setText("?");
                    mineLabel.setText("Mines Left: " + 
                            mineCountDisplay);
                }
                //if cell is a mine, displays '!'
                else if (iCell.isMine()) {
                    board[r][c].setText("!");
                }
                //if cell isn't exposed, display nothing
                else if (!iCell.isExposed()) {
                    board[r][c].setText("");
                }

                //if the game is lost, show all mines
                if (game.getGameStatus() == GameStatus.Lost) {
                    if (iCell.isMine()) {
                        board[r][c].setEnabled(false);
                    }

                }
            }
    }

    /******************************************************************
     * Private helper class that handles all mouse events that occur in
     * the game
     *****************************************************************/
    private class mouse implements MouseListener {
        //overrides required by the interface
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //cycles through all cells to find source of event
            for (int r = 0; r < boardSize; r++)
                for (int c = 0; c < boardSize; c++)
                    if (board[r][c] == e.getSource()) {
                        //if event was a right click
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            //unflag cell if it's already flagged
                            if (game.getCell(r, c).isFlagged()) {
                                game.getCell(r, c).setFlagged(false);
                                mineCountDisplay++;
                                
                            //flag cell if it's not already
                            } else {
                                game.getCell(r, c).setFlagged(true);
                                mineCountDisplay--;
                            }
                        }
                        //if event was a left click
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            //if the cell isn't flagged, select cell
                            if (!game.getCell(r, c).isFlagged()){
                                game.select(r, c);
                                actionCounter++;
                            }
                        }
                    }
            
            //display the game board
            displayBoard();


            //if quit button was pressed, quit game after confirmation
            if (e.getSource() == quitButton) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int confirm = JOptionPane.showConfirmDialog
                            (null, "Quit Game?", "QUIT",
                                    JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            }

            /*
             *if recursive button pressed, change whether the game
             * handles zero fill recursively or non recursively
             */
            
            if (e.getSource() == recursiveButton){
                if (recursionLabel.getText().equals("Recursive Zero Fill")) {
                    recursionLabel.setText("Non-Recursive Zero Fill");
                    game.setUseRecursion(false);
                }else if (recursionLabel.getText().equals
                        ("Non-Recursive Zero Fill")) {
                    recursionLabel.setText("Recursive Zero Fill");
                    game.setUseRecursion(true);
                }
            }


            if (game.getGameStatus() == GameStatus.Lost) {
                //update game board
                displayBoard();
                //display losing message if game is lost
                JOptionPane.showMessageDialog(null, "You Lose \n" +
                        "The game will reset");

                //reset game
                game.reset();
                
                //increase losses count
                losses++;
                lossesLabel.setText("Losses: " + losses);
                
                //reset display board and mines
                mineCountDisplay = mineCount;
                mineLabel.setText("Mines Left: " + mineCountDisplay);
                displayBoard();

            }

            if (game.getGameStatus() == GameStatus.WON) {
                //display winning message if game is won
                JOptionPane.showMessageDialog(null, "You Win: all " +
                        "mines have been found!\n The game will reset");
                
                //reset game
                game.reset();
                
                //increment number of game wins
                wins++;
                winsLabel.setText("Wins: " + wins);
                
                //reset the display
                mineCountDisplay = mineCount;
                mineLabel.setText("Mines Left: " + mineCountDisplay);
                displayBoard();
            }

        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }



    }

}






