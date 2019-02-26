package project2;

import javax.swing.*;

/**********************************************************************
 * A class that creates the MineSweeper Graphical User Interface
 *
 * Jillian Huizenga and Lauren Freeman
 * CIS 163-01 Professor Ferguson
 * 26 February 2019
 *********************************************************************/
public class MineSweeperGUI {

    /** a static int that stores the chosen board size **/
    private static int boardSize;

    /** a static int that stores the chosen mine count **/
    private static int mineCount;

    /** a static String that stores the chosen board size **/
    private static String boardSizeStr = "";

    /** a static String that stories the chosen mine count **/
    private static String mineCountStr = "";

    /******************************************************************
     * A private helper method that prompts the user for a board size
     * that must be between 3 and 30. Loops until an acceptable value
     * is given
     *****************************************************************/
    private static void promptBoardSize() {
        try {
            do {
                if (boardSizeStr.length() > 0) {
                    JOptionPane.showMessageDialog(null, "Please " +
                                    "choose a number between 3 and 30",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }
                boardSizeStr = JOptionPane.showInputDialog(null,
                        "Enter in the size of the board (3-30): ");
            } while (boardSizeStr.equals("") ||
                    Integer.parseInt(boardSizeStr) < 3 ||
                    Integer.parseInt(boardSizeStr) > 30);
        } catch (NumberFormatException e) {
            promptBoardSize();
        }

        boardSize = Integer.parseInt(boardSizeStr);
    }

    /******************************************************************
     * A private helper method that prompts the user for a mine count.
     * The default value if an error occurs is 10 for all cases except
     * when board size is 3 (then it is 2)
     * (contains inner comments)
     *****************************************************************/
    private static void promptMineCount() {
        try {
            mineCountStr = JOptionPane.showInputDialog(null,
                    "Enter in the desired number of mines " +
                            "(default is 10): ");

            // set mines to 10 or 2 if less than 1 or over cell count
            if (Integer.parseInt(mineCountStr) < 1 ||
                    Integer.parseInt(mineCountStr) >
                            Integer.parseInt(boardSizeStr) *
                                    Integer.parseInt(boardSizeStr)) {
                if (boardSize > 3)
                    mineCountStr = "10";
                else
                    mineCountStr = "2";
            }

        } catch (NumberFormatException e) {
            if (boardSize > 3)
                mineCountStr = "10";
            else
                mineCountStr = "2";
        }

        mineCount = Integer.parseInt(mineCountStr);
    }

    /******************************************************************
     * The main method
     * (contains inner comments)
     * @param args an array of String objects
     *****************************************************************/
    public static void main(String[] args) {

        // prompt user for board size (able to exit program)
        try {
            promptBoardSize();
        } catch (NullPointerException e) {
            System.exit(0);
        }

        // prompt user for mine count (default values exist)
        promptMineCount();

        // set up GUI
        JFrame frame = new JFrame("MineSweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MineSweeperPanel panel = new MineSweeperPanel(boardSize, mineCount);
        frame.getContentPane().add(panel);
        frame.setSize((40 * boardSize) + 50, (40 * boardSize) + 50);
        frame.setVisible(true);
    }
    
    
}
